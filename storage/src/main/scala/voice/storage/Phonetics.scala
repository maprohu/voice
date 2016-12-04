package voice.storage

import java.nio.ByteBuffer


object Vowels extends Enumeration {
  def Vowel = Value

  val MaxBitSize = 7
  val MaxSize = 1 << (MaxBitSize - 1)
  val BitMask = MaxSize - 1

  val A = Vowel
  val AA = Vowel
  val E = Vowel
  val EE = Vowel
  val I = Vowel
  val O = Vowel
  val OE = Vowel
  val U = Vowel
  val UE = Vowel

}

object Consonants extends Enumeration {
  def Consonant = Value

  val MaxBitSize = 7
  val MaxSize = 1 << (MaxBitSize - 1)
  val BitMask = MaxSize - 1

  val B = Consonant
  val C = Consonant
  val CS = Consonant
  val D = Consonant
  val DZ = Consonant
  val DZS = Consonant
  val G = Consonant
  val GY = Consonant
  val H = Consonant
  val J = Consonant
  val K = Consonant
  val L = Consonant
  val M = Consonant
  val N = Consonant
  val NY = Consonant
  val P = Consonant
  val R = Consonant
  val S = Consonant
  val SZ = Consonant
  val T = Consonant
  val TY = Consonant
  val V = Consonant
  val Z = Consonant
  val ZS = Consonant

}

object Syllables {
  type Code = Short

  case class Syllable(
    consonant: Consonants.Value,
    vowel: Vowels.Value
  ) {
    val code : Code = (((consonant.id & Consonants.BitMask) << Vowels.MaxBitSize) | vowel.id).toShort
    val codeBytes = ByteBuffer.allocate(java.lang.Short.BYTES).putShort(code).array()
    val string = consonant.toString().toLowerCase ++ vowel.toString().toLowerCase
  }

  val Items =
    (for {
      c <- Consonants.values
      v <- Vowels.values
    } yield {
      Syllable(c, v)
    }).toVector


  val lookup : Map[Short, Syllable] =
    Items
      .map(i => i.code -> i)
      .toMap

}
