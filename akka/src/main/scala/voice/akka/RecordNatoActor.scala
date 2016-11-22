package voice.akka

import javax.sound.sampled.{AudioSystem, SourceDataLine}

import akka.actor.Actor
import akka.event.Logging
import voice.core.SingleMixer.SoundForm
import voice.core.events.{ButtonB, ButtonHigh, LogicalClick}
import voice.core.{Consonants, NatoAlphabet, SingleMixer, Vowels}

import scala.util.Random

/**
  * Created by maprohu on 20-11-2016.
  */
class RecordNatoActor(
  config: RecordNatoActor.Config
) extends Actor {
  val log = Logging(context.system, this)
  import context.dispatcher

//  log.info(
//    AudioTools.dumpInfo
//  )

  val MixerName = "Audio [plughw:1,0]"

  val mixer = SingleMixer(
    SingleMixer.Config(
      sourceLineFinder = { format =>
        val m = AudioSystem
          .getMixer(
            AudioSystem
              .getMixerInfo
              .find(m =>
                m.getName == MixerName ||
                m.getDescription == MixerName
              )
              .get
          )

        m.
          getLine(
            m.getSourceLineInfo()(0)
          )
          .asInstanceOf[SourceDataLine]
      }
    )
  )

  val nato = NatoAlphabet.cache(mixer)

  val beep =
    mixer
      .render(
        SoundForm(
          seconds = 0.3f,
          form = { t =>
            math.sin(440 * math.Pi * 2 * t).toFloat
          }
        )
      )

  val vs = Vowels.values.toIndexedSeq
  val cs = Consonants.values.toIndexedSeq

  log.info("nato started")

  override def receive: Receive = {
    case LogicalClick(ButtonHigh) =>
      beep.play
    case LogicalClick(ButtonB) =>
      val c = cs(Random.nextInt(cs.length))
      val v = vs(Random.nextInt(vs.length))

      val str =
        s"${c.toString().toLowerCase}${v.toString().toLowerCase}"

      NatoAlphabet.readString(
        str,
        nato
      )

    case msg =>
      log.info("nato: {}", msg)
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    mixer.stop()

    super.postStop()
  }
}

object RecordNatoActor {
  case class Config(

  )

}

