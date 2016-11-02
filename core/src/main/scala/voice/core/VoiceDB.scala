package voice.core

import toolbox8.leveldb.Keys
import voice.core.Phones.{Phone, PhoneID}

import scala.collection.immutable._

/**
  * Created by maprohu on 02-11-2016.
  */
object VoiceDB {

  sealed trait Key[V]
  sealed trait RecordingsKey extends Key[RecordingValue]
  case object RecordingsKey0 extends RecordingsKey
  case class RecordingKey(
    id: Int
  ) extends Key[RecordingValue]

  sealed trait RecordingsValue
  case class RecordingsValue0(
    nextId: Int,
    items: Seq[(Seq[PhoneID])]
  ) extends RecordingsValue

  sealed trait RecordingValue
  case class RecordingValue0(
    rate: Float,
    data: Array[Float]
  ) extends RecordingValue

}
