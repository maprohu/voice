package voice.sandbox.training

import java.io.File

import monix.execution.Cancelable
import monix.execution.cancelables.CompositeCancelable
import org.mapdb.{DBMaker, Serializer}
import voice.storage.Syllables.Syllable

/**
  * Created by maprohu on 30-12-2016.
  */
object DBEntity extends Enumeration {
  val

    RecordingMeta,
    RecordingData

  = Value
}

object TrainingDB {

  val Location = "../voice/local/trainingdb/mapdb.data"

  val cancel = {
    val c = CompositeCancelable()

    Runtime.getRuntime.addShutdownHook(
      new Thread() {
        override def run(): Unit = {
          c.cancel()
        }
      }
    )

    c
  }

  lazy val db = {
    val file = new File(Location)
    file.getParentFile.mkdirs()

    val db =
      DBMaker
        .fileDB(
          file
        )
        .fileMmapEnableIfSupported()
        .transactionEnable()
        .make()

    cancel += Cancelable({ () =>
      db.close()
    })

    db
  }

  lazy val recordingMeta = {
    db
      .atomicVar(
        DBEntity.RecordingMeta.toString,
        Serializer.JAVA.asInstanceOf[Serializer[Recordings]],
        Recordings()
      )
      .createOrOpen()
  }

  lazy val recordingData = {
    db
      .treeMap(DBEntity.RecordingData.toString)
      .keySerializer(Serializer.LONG)
      .valueSerializer(Serializer.BYTE_ARRAY)
      .createOrOpen()
  }




}

case class Recordings(
  nextId : Long = 0,
  data: Map[Syllable, Vector[Long]] = Map()
)


