package voice.central

import java.io.File

import com.typesafe.scalalogging.StrictLogging
import org.mapdb.{DBMaker, Serializer}
import toolbox6.logging.LogTools
import toolbox8.jartree.streamapp.Plugged

/**
  * Created by maprohu on 26-11-2016.
  */
class CentralPlugged extends Plugged with StrictLogging with LogTools {
  val Dir = new File("/opt/voicer/db")
  Dir.mkdirs()
  val DbFile = new File(Dir, "mapdb")

  logger.info("creating mapdb")
  val db =
    DBMaker
      .fileDB(DbFile)
      .fileMmapEnableIfSupported()
      .transactionEnable()
      .make()

  val publicKeys =
    db
      .hashMap("publicKeys")
      .keySerializer(Serializer.STRING)
      .valueSerializer(Serializer.STRING)
      .createOrOpen()

  override def preUnplug: Any = {
    logger.info("closing mapdb")
    quietly { db.commit() }
    quietly { db.close() }
  }

  override def postUnplug: Unit = {}
}
