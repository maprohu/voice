package voice.sandbox

import scala.concurrent.forkjoin.ThreadLocalRandom

/**
  * Created by pappmar on 05/09/2016.
  */
object RunVoiceGenerate {

  val MinConsonants = 1
  val MaxConsonants = 10

  def main(args: Array[String]): Unit = {
    Iterator.continually(
      ThreadLocalRandom.current().nextInt(MinConsonants, MaxConsonants+1)
    )
      .map({ consonantCount =>
        val vowelCount = consonantCount + 1

        Utils
          .roundRobin(
            Utils.randomSequence(Sounds.Vowels),
            Utils.randomSequence(Sounds.Consonants)
          )
          .map(_.head.next())
          .take(consonantCount + vowelCount)
      })
      .foreach({ sounds =>
        println(sounds.mkString(" "))
      })


  }

}
