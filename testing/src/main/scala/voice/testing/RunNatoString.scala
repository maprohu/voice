package voice.testing

import java.util.concurrent.Executors

import voice.core.NatoAlphabet
import voice.storage.{Consonants, Vowels}

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
    implicit val scheduler = Executors.newSingleThreadScheduledExecutor()

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
