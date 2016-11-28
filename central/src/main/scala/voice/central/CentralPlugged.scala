package voice.central

import java.io.File

import com.typesafe.scalalogging.StrictLogging
import org.mapdb.{DBMaker, Serializer}
import toolbox6.logging.LogTools
import toolbox8.jartree.requestapi.RequestMarker
import toolbox8.jartree.streamapp.Plugged
import voice.api.updateclientinfo.ClientInfo

/**
  * Created by maprohu on 26-11-2016.
  */
class CentralPlugged extends Plugged with StrictLogging with LogTools {
  val Dir = new File("/opt/voicer/db")
  Dir.mkdirs()
  val DbFile = new File(Dir, "mapdb")

  logger.info(s"creating mapdb at: ${DbFile}")
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

  val clientInfo =
    db
      .hashMap("clientInfo")
      .keySerializer(Serializer.INTEGER)
      .valueSerializer(Serializer.JAVA.asInstanceOf[Serializer[ClientInfo]])
      .createOrOpen()

  override def preUnplug: Any = {
    logger.info("closing mapdb")
    quietly { db.commit() }
    quietly { db.close() }
  }

  override def postUnplug: Unit = {}

  override def marked[In, Out](marker: RequestMarker[In, Out], in: In): Out = {
    marker match {
      case ClientInfo.Update =>
        val data = in.asInstanceOf[ClientInfo]
        logger.info(s"updating client info: ${data}")
        clientInfo.put(data.clientId, data)
        db.commit()
        ().asInstanceOf[Out]

      case ClientInfo.ReadAll =>
        import scala.collection.JavaConversions._
        clientInfo
          .values()
          .toVector
          .asInstanceOf[Out]

      case ClientInfo.Read =>
        val id = in.asInstanceOf[Int]
        Option
          .apply(
            clientInfo.get(id)
          )
          .asInstanceOf[Out]

      case _ => ???
    }
  }
}
