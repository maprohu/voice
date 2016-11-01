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


object Phones extends Enumeration {
//  object Implicits {
//    implicit def toPhone(v: Val) : Phone = v.asInstanceOf[Phone]
//  }
  final case class Phone(
    word: String,
    vocalTract: VocalTract
  ) extends Val

  lazy val toSet = values.asInstanceOf[collection.immutable.SortedSet[Phone]]


  val A = Phone(
    "alfa",
    Vowel()
  )
  val AA = Phone(
    "ugly",
    Vowel()
  )
  val E = Phone(
    "echo",
    Vowel()
  )
  val EE = Phone(
    "amy",
    Vowel()
  )
  val I = Phone(
    "india",
    Vowel()
  )
  val O = Phone(
    "oscar",
    Vowel()
  )
  val OE = Phone(
    "urgent",
    Vowel()
  )
  val U = Phone(
    "uniform",
    Vowel()
  )
  val UE = Phone(
    "uber",
    Vowel()
  )


  val B = Phone(
    "bravo",
    Consonant()
  )
  val C = Phone(
    "zwack",
    Consonant()
  )
  val CS = Phone(
    "charlie",
    Consonant()
  )
  val D = Phone(
    "delta",
    Consonant()
  )
  val DZ = Phone(
    "jazzy",
    Consonant()
  )
  val DZS = Phone(
    "joker",
    Consonant()
  )
  val G = Phone(
    "golf",
    Consonant()
  )
  val GY = Phone(
    "dewberry",
    Consonant()
  )
  val H = Phone(
    "hotel",
    Consonant()
  )
  val J = Phone(
    "ewing",
    Consonant()
  )
  val K = Phone(
    "kilo",
    Consonant()
  )
  val L = Phone(
    "lima",
    Consonant()
  )
  val M = Phone(
    "mike",
    Consonant()
  )
  val N = Phone(
    "november",
    Consonant()
  )
  val NY = Phone(
    "newborn",
    Consonant()
  )
  val P = Phone(
    "poker",
    Consonant()
  )
  val R = Phone(
    "romeo",
    Consonant()
  )
  val S = Phone(
    "sugar",
    Consonant()
  )
  val SZ = Phone(
    "sierra",
    Consonant()
  )
  val T = Phone(
    "tango",
    Consonant()
  )
  val TY = Phone(
    "matthew",
    Consonant()
  )
  val V = Phone(
    "victor",
    Consonant()
  )
  val Z = Phone(
    "zulu",
    Consonant()
  )
  val ZS = Phone(
    "closure",
    Consonant()
  )




}
