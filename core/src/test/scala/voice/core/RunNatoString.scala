package voice.core
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 19-11-2016.
  */
object RunNatoString {

  val text = "dzsue"

  def main(args: Array[String]): Unit = {
//    NatoAlphabet.readString(text)

    val vs = Phones.Vowels.toIndexedSeq
    val cs = Phones.Consonants.toIndexedSeq

    while (true) {
      val c = cs(Random.nextInt(cs.length))
      val v = vs(Random.nextInt(vs.length))

      val str =
        s"${c.toString().toLowerCase}${v.toString().toLowerCase}"

      NatoAlphabet.readString(str)

      StdIn.readLine()

    }



  }

}
