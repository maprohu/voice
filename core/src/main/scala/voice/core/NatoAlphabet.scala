package voice.core

import javax.sound.sampled.AudioSystem

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

}
