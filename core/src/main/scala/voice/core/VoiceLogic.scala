package voice.core

import java.io.{ByteArrayOutputStream, FileInputStream, InputStream}
import java.util.concurrent.{Executors, TimeUnit}

import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.Cancelable
import monix.execution.cancelables.AssignableCancelable
import toolbox6.logging.LogTools
import voice.core.ShortLongProcessor._
import voice.core.SingleMixer.SoundForm
import voice.core.SingleRecorder.RecorderProcessor
import voice.core.Syllables.Syllable
import voice.core.events.{ButtonA, ButtonB, ButtonC, ControllerEvent}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random

/**
  * Created by pappmar on 22/11/2016.
  */
object VoiceLogic extends StrictLogging {

  def connectToDevice = {
    val deviceFile =
      HidParser.HidFilePath.toFile

    while (!deviceFile.exists()) {
      logger.info("device file not present, waiting...")
      Thread.sleep(1000)
    }

    new FileInputStream(
      deviceFile
    )
  }

  def run(
    deviceConnection: () => InputStream = () => connectToDevice
  )(implicit
    executionContext: ExecutionContext
  ): Cancelable = {

    HidPhysicalThread.run({ () =>
      logger.info("starting hid reading")

      val is = deviceConnection()

      val logic = new VoiceLogic

      val p =
        new HidSingleProcessor(
          new HidLongProcessor(
            new ShortLongWrapper(
              logic.Start
            )
          )
        )

      (is, p)
    })

  }


}

class VoiceLogic(implicit
  executionContext: ExecutionContext
) extends StrictLogging with LogTools {

  val OkButton = Down(ButtonB)
  val CancelButton = Down(ButtonA)
  val RepeatButton = Down(ButtonC)

  val RecordingTimeoutDuration = 2.seconds

  val mixer = SingleMixer()
  val recorder = SingleRecorder()
  val nato = NatoAlphabet.cache(mixer)
  val ignored = mixer.render(SoundForm.sine(0.1f, 220f, 0.5f))
  val startRecording = mixer.render(SoundForm.sine(0.5f, 880f, 1f))
  val cancelRecording = mixer.render(SoundForm.sine(0.3f, 110f, 1f))
  val scheduler = Executors.newSingleThreadScheduledExecutor()

  def shutdown() = {
    quietly { mixer.stop() }
    quietly { recorder.stop() }
    quietly {
      scheduler.shutdown()
      scheduler.awaitTermination(5, TimeUnit.SECONDS)
    }
  }

  abstract class Base extends Wrapped {
    final override def process(event: Wrap): StatefulProcessor[Wrap] = {
      event match {
        case c: Click =>
          click(c)
        case _ =>
          stop()
          shutdown()
          Stopped
      }
    }

    def click(c: Click): Wrapped

    def stop() : Unit = ()
  }

  def ignore(w: Wrap) = {
    w match {
      case _ : Down =>
        logger.info("ignored: {}", w)
        ignored.play
      case _ =>
    }
  }

  object Stopped extends Wrapped {
    override def process(event: Wrap): StatefulProcessor[Wrap] = {
      ignore(event)
      this
    }
  }

  object Start extends Base {
    override def click(c: Click): Wrapped = c match {
      case Down(ButtonB) =>
        new Playing
      case _ =>
        ignore(c)
        this
    }
  }

  class Playing extends Base {
    val syllable = Syllables.Items(Random.nextInt(Syllables.Items.length))
    logger.info("playing: {}", syllable)

    val readStatus = NatoAlphabet.readString(
      syllable.string,
      nato
    )

    override def click(c: Click): Wrapped = {
      if (readStatus.isCompleted) {
        c match {
          case CancelButton =>
            new Playing
          case OkButton =>
            new Recording(syllable)
          case _ =>
            ignore(c)
            this
        }
      } else {
        ignore(c)
        this
      }
    }
  }

  class Recording(
    syllable: Syllable
  ) extends Base {
    val recording = AssignableCancelable.single()
    @volatile var timeout = false

    val data = new ByteArrayOutputStream()

    val startFuture =
      for {
        _ <- startRecording.play
      } yield {
        scheduler.schedule(
          new Runnable {
            override def run(): Unit = {
              recording.cancel()
              timeout = true
            }
          },
          RecordingTimeoutDuration.length,
          RecordingTimeoutDuration.unit
        )

        recording := Cancelable(
          recorder.record(
            new RecorderProcessor {
              override def process(chunk: Array[Byte]): Unit = data.write(chunk)
            }
          )
        )
      }

    override def click(c: Click): Wrapped = {
      if (timeout) {
        Start.process(c)
      } else {
        c match {
          case CancelButton =>
            recording.cancel()
            cancelRecording.play
            Start

          case OkButton if startFuture.isCompleted =>
            recording.cancel()
            new Replaying(
              syllable,
              data.toByteArray
            )

        }

      }
    }
  }

  class Replaying(
    syllable: Syllable,
    data: Array[Byte]
  ) extends Base {
    val recorded = mixer.sampled(
      WaveFile.samples(
        data,
        1,
        false
      )
    )
    recorded.play

    override def click(c: Click): Wrapped = c match {
      case RepeatButton =>
        recorded.play
        this

    }
  }


}

