package voice.core

/**
  * Created by maprohu on 19-11-2016.
  */
object RunLoadNato {

  def main(args: Array[String]): Unit = {
    val a = NatoAlphabet.load('c')

    val mixer = SingleMixer()
    mixer.render(a).play
  }

}
