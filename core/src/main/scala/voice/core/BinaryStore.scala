package voice.core

import java.io.File
import java.nio.ByteBuffer

import com.typesafe.scalalogging.LazyLogging
import org.iq80.leveldb.Options
import org.iq80.leveldb.impl.Iq80DBFactory._

/**
  * Created by maprohu on 20-11-2016.
  */
class BinaryStore(
  dbDir: File
) extends LazyLogging {
  import BinaryStore._

  val db = {
    logger.info(s"BinaryStore using: ${dbDir}")
    dbDir.mkdirs()
    val options = new Options
    options.createIfMissing(true)

    factory.open(
      dbDir,
      options
    )
  }



  val nextId = new Object {
    private var id = {
      val nib = db.get(LastIdKeyBytes)
      if (nib == null) {
        InitialLastId
      } else {
        fromBytes(nib)
      }
    }

    def get(count: Long = 1) = synchronized {
      id += count
      val idb = toBytes(id)
      db.put(LastIdKeyBytes, idb)
      idb
    }
  }



  def write(
    data: Array[Byte]
  ) = {
    val id = nextId.get()
    db.put(id, data)
    id
  }

}

object BinaryStore {
  type Key = Array[Byte]

  val LastIdKey = 0L
  val LastIdKeyBytes = toBytes(LastIdKey)
  val InitialLastId = LastIdKey

  def toBytes(v: Long) : Array[Byte] = {
    ByteBuffer
      .allocate(java.lang.Long.BYTES)
      .putLong(v)
      .array()
  }

  def fromBytes(b: Array[Byte]) : Long = {
    ByteBuffer
      .wrap(b)
      .getLong
  }
}
