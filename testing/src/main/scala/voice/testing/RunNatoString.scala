package voice.testing

import voice.core.{Consonants, NatoAlphabet, Vowels}

import scala.io.StdIn
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by maprohu on 19-11-2016.
  */
object RunNatoString {

  val text = "dzsue"

  def main(args: Array[String]): Unit = {
//    NatoAlphabet.readString(text)

    val vs = Vowels.values.toIndexedSeq
    val cs = Consonants.values.toIndexedSeq

    while (true) {
      val c = cs(Random.nextInt(cs.length))
      val v = vs(Random.nextInt(vs.length))

      val str =
        s"${c.toString().toLowerCase}${v.toString().toLowerCase}"

      println(str)

      NatoAlphabet.readString(str)

      StdIn.readLine()

    }



  }

}
