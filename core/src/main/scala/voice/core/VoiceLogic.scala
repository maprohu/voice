package voice.core

import java.io.{FileInputStream, InputStream}

import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.Cancelable
import toolbox6.logging.LogTools
import voice.core.ShortLongProcessor._
import voice.core.SingleMixer.SoundForm
import voice.core.events.{ButtonA, ButtonB, ControllerEvent}

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

class VoiceLogic extends StrictLogging with LogTools {

  val mixer = SingleMixer()
  val nato = NatoAlphabet.cache(mixer)
  val ignored = mixer.render(SoundForm.sine(0.1f, 220f, 0.5f))

  def shutdown() = {
    quietly { mixer.stop() }
  }

  abstract class Base extends Wrapped {
    final override def process(event: Wrap): StatefulProcessor[Wrap] = {
      event match {
        case c: Click =>
          click(c)
        case _ =>
          shutdown()
          Stopped
      }
    }

    def click(c: Click): Wrapped
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

    val readStatus = NatoAlphabet.readString(
      syllable.string,
      nato
    )

    override def click(c: Click): Wrapped = {
      if (readStatus.isCompleted) {
        c match {
          case Down(ButtonA) =>
            new Playing
          case Down(ButtonB) =>
            // do recording
            this
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


}

