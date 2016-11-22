package voice.core

import java.io._
import java.util.concurrent.{Executors, TimeUnit}

import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.Cancelable
import monix.execution.cancelables.AssignableCancelable
import toolbox6.logging.LogTools
import toolbox8.leveldb.{IntSize, LevelDB}
import voice.core.ShortLongProcessor.{Click, Down, Wrap, Wrapped}
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
object VoiceLogic extends StrictLogging with LogTools {

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
    dbDir : File,
    deviceConnection: () => InputStream = () => connectToDevice
  )(implicit
    executionContext: ExecutionContext
  ): Cancelable = {

    val db = LevelDB(dbDir)

    val hidCancelable = HidPhysicalThread.run({ () =>
      logger.info("starting hid reading")

      val is = deviceConnection()

      val logic = new VoiceLogic(db)

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

    Cancelable({ () =>
      quietly { hidCancelable.cancel() }
      quietly { db.cancelable.cancel() }
    })

  }


}

object Tables extends Enumeration {
  val

    Recordings

  = Value
}

case class Recordings(
  lookup : Map[Vector[Syllable], Vector[Long]] = Map.empty
)

class VoiceLogic(
  db: LevelDB
)(implicit
  executionContext: ExecutionContext
) extends StrictLogging with LogTools {

  val blobs = db.longTable()

  def read[T](id: Tables.Value) : Option[T] = {
    Option(
      db.db.get(
        IntSize.toArray(id.id)
      )
    ).map({ b =>
      val is = new ObjectInputStream(
        new ByteArrayInputStream(
          b
        )
      )

      is
        .readObject()
        .asInstanceOf[T]
    })
  }

  def write[T](id: Tables.Value, value: T) : Unit = {
    val os = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(os)
    oos.writeObject(value)
    oos.close()
    db.db.put(
      IntSize.toArray(id.id),
      os.toByteArray
    )
  }

  val OkButton = Down(ButtonB)
  val CancelButton = Down(ButtonA)
  val RepeatButton = Down(ButtonC)

  val RecordingTimeoutDuration = 5.seconds

  val mixer = SingleMixer()
  val recorder = SingleRecorder()
  val nato = NatoAlphabet.cache(mixer)
  val ignored = mixer.render(SoundForm.sine(0.1f, 220f, 0.5f))
  val startRecording = mixer.render(SoundForm.sine(0.5f, 880f, 1f))
  val cancelRecording = mixer.render(SoundForm.sine(0.3f, 220f, 1f))
  val scheduler = Executors.newSingleThreadScheduledExecutor()

  def shutdown() = {
    quietly {
      logger.info("shutting down scheduler")
      scheduler.shutdown()
      scheduler.awaitTermination(5, TimeUnit.SECONDS)
      logger.info("scheduler shut down")
    }
    quietly { mixer.stop() }
    quietly { recorder.stop() }
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
        val timeoutFuture = scheduler.schedule(
          new Runnable {
            override def run(): Unit = {
              logger.info(s"recording timeout after ${RecordingTimeoutDuration}")

              cancelRecording.play
              recording.cancel()
              timeout = true
            }
          },
          RecordingTimeoutDuration.length,
          RecordingTimeoutDuration.unit
        )

        val stopRecording =
          recorder.record(
            new RecorderProcessor {
              override def process(chunk: Array[Byte]): Unit = data.write(chunk)
            }
          )

        recording := Cancelable({ () =>
          timeoutFuture.cancel(false)
          stopRecording.cancel()
        })
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
            if (timeout) {
              Start
            } else {
              new Replaying(
                syllable,
                data.toByteArray
              )
            }

          case _ =>
            ignore(c)
            this

        }

      }
    }
  }

  var recordings = read[Recordings](Tables.Recordings).getOrElse(Recordings())
  logger.info(s"recording db has ${recordings.lookup.size} entries with ${recordings.lookup.values.map(_.length).sum} recordings")

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
      case OkButton =>
        val word = Vector(syllable)

        logger.info(
          s"saving: {}, ${data.length} bytes, ${data.length / recorder.config.bytesPerSample / recorder.config.samplesPerSecond} seconds",
          word
        )

        val key = blobs.insert(data)
        recordings = recordings.copy(
          lookup = recordings.lookup.updated(
            word,
            recordings.lookup.getOrElse(word, Vector.empty[Long]) :+ key
          )
        )
        write(Tables.Recordings, recordings)
        logger.info("saved with id: {}", key)
        new Playing
      case CancelButton =>
        logger.info("not saved")
        Start
      case _ =>
        ignore(c)
        this

    }
  }


}

