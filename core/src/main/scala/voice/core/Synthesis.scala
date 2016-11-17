package voice.core

/**
  * Created by maprohu on 17-11-2016.
  */
object Synthesis {

  case class AudioUnit(
    length: Double,
    form: Double => Double
  )


  val Units : IndexedSeq[AudioUnit] = IndexedSeq(

  )

}
