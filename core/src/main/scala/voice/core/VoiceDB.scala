package voice.core


import scala.collection.immutable._

/**
  * Created by maprohu on 02-11-2016.
  */
object VoiceDB {

  sealed trait Key[V]
  sealed trait RecordingsKey extends Key[RecordingValue]
  case object RecordingsKey0 extends RecordingsKey

  sealed trait RecordingKey extends Key[RecordingValue]
  case class RecordingKey0(
    id: Int
  ) extends RecordingKey

  sealed trait RecordingsValue
//  case class RecordingsValue0(
//    nextId: Int,
//    items: Seq[(RecordingKey, Seq[PhoneID])]
//  ) extends RecordingsValue

  sealed trait RecordingValue
  case class RecordingValue0(
    rate: Float,
    data: Array[Float]
  ) extends RecordingValue

}
