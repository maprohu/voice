package voice.core


/**
  * Created by maprohu on 01-11-2016.
  */
object Phonetics {

}

sealed trait VocalTract
case class Vowel(

) extends VocalTract
case class Consonant(

) extends VocalTract

case class Phone(
  id: String,
  vocalTract: VocalTract
)

object Phones {
  import toolbox6.macros.Macros.{valName => id}

  val A = Phone(
    id,
    Vowel()
  )
  val AA = Phone(
    id,
    Vowel()
  )
  val E = Phone(
    id,
    Vowel()
  )
  val EE = Phone(
    id,
    Vowel()
  )
  val I = Phone(
    id,
    Vowel()
  )
  val O = Phone(
    id,
    Vowel()
  )
  val OE = Phone(
    id,
    Vowel()
  )
  val U = Phone(
    id,
    Vowel()
  )
  val UE = Phone(
    id,
    Vowel()
  )



  val B = Phone(
    id,
    Consonant()
  )
  val C = Phone(
    id,
    Consonant()
  )
  val CS = Phone(
    id,
    Consonant()
  )
  val D = Phone(
    id,
    Consonant()
  )
  val DZ = Phone(
    id,
    Consonant()
  )
  val DZS = Phone(
    id,
    Consonant()
  )
  val G = Phone(
    id,
    Consonant()
  )
  val GY = Phone(
    id,
    Consonant()
  )
  val H = Phone(
    id,
    Consonant()
  )
  val J = Phone(
    id,
    Consonant()
  )
  val K = Phone(
    id,
    Consonant()
  )
  val L = Phone(
    id,
    Consonant()
  )
  val M = Phone(
    id,
    Consonant()
  )
  val N = Phone(
    id,
    Consonant()
  )
  val NY = Phone(
    id,
    Consonant()
  )
  val P = Phone(
    id,
    Consonant()
  )
  val R = Phone(
    id,
    Consonant()
  )
  val S = Phone(
    id,
    Consonant()
  )
  val SZ = Phone(
    id,
    Consonant()
  )
  val T = Phone(
    id,
    Consonant()
  )
  val TY = Phone(
    id,
    Consonant()
  )
  val V = Phone(
    id,
    Consonant()
  )
  val Z = Phone(
    id,
    Consonant()
  )
  val ZS = Phone(
    id,
    Consonant()
  )




}
