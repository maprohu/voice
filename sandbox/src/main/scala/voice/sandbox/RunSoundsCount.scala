package voice.sandbox

/**
  * Created by pappmar on 05/09/2016.
  */
object RunSoundsCount {

  def main(args: Array[String]): Unit = {
    println(
      s"""vowels: ${Sounds.Vowels.size}
         |consonants: ${Sounds.Consonants.size}
         |pairs: ${Sounds.Vowels.size * Sounds.Consonants.size}
      """.stripMargin

    )

    println(
      RunVoiceTeach
        .Items
        .zipWithIndex
        .map({ case (sounds, idx) => s"$idx: ${sounds.mkString(" ")}"})
        .mkString("\n")

    )
  }

}
