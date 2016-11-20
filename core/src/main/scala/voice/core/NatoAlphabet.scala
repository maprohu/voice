package voice.core

import javax.sound.sampled.AudioSystem

import com.typesafe.scalalogging.LazyLogging
import voice.core.SingleMixer.PlayableSound

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by maprohu on 19-11-2016.
  */
object NatoAlphabet extends LazyLogging {

  def load(c: Char) = {
    val ais = AudioSystem.getAudioInputStream(
      getClass.getResource(s"/nato/${c}.wav")
    )

    WaveFile.samples(ais, true)
  }

  def cache(mixer: SingleMixer) = {
    logger.info("loading nato")

    val map = ('a' to 'z')
      .map({ c =>
        logger.info(s"loading nato: ${c}")
        c -> mixer.sampled(load(c))
      })
      .toMap

    logger.info("nato loaded")
    map
  }

  lazy val Cache = cache(SingleMixer.Global)

  def readString(
    text: String,
    fn: Char => PlayableSound = Cache
  )(implicit
    executionContext: ExecutionContext
  ) : Future[Unit] = {
    if (text.isEmpty) Future.successful()
    else {
      fn(text.head)
        .play
        .flatMap(_ => readString(text.tail, fn))
    }

  }

}
