package voice.core

import javax.sound.sampled.AudioSystem

import voice.core.SingleMixer.PlayableSound

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by maprohu on 19-11-2016.
  */
object NatoAlphabet {

  def load(c: Char) = {
    val ais = AudioSystem.getAudioInputStream(
      getClass.getResource(s"/nato/${c}.wav")
    )

    WaveFile.toSound(ais)
  }

  lazy val Cache =
    ('a' to 'z')
      .map({ c =>
        c -> SingleMixer.Global.render(load(c))
      })
      .toMap

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
