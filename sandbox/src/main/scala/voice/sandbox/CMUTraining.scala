package voice.sandbox

import javax.sound.sampled.{AudioFormat, AudioSystem}


/**
  * Created by pappmar on 04/01/2017.
  */
object CMUTraining {

  val Format = new AudioFormat(
    16000,
    16,
    1,
    true,
    false
  )

}
