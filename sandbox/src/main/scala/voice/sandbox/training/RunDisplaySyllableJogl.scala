package voice.sandbox.training

/**
  * Created by maprohu on 02-01-2017.
  */
object RunDisplaySyllableJogl {

  def main(args: Array[String]): Unit = {
    import voice.storage.Consonants._
    import voice.storage.Vowels._
    DisplayJogl.show(Display.data(
      F, OE
    ))
  }

}
