package voice.requests.central

import java.util

import toolbox8.jartree.streamapp.Requestable
import voice.central.CentralPlugged
import ammonite.ops._
import com.typesafe.scalalogging.StrictLogging

import scala.collection.JavaConversions._

/**
  * Created by maprohu on 26-11-2016.
  */
object PutPublicKey {
  case class Input(
    client: String,
    publicKey: String
  )
}
import PutPublicKey._
class PutPublicKey extends Requestable[CentralPlugged, Input, Unit] with StrictLogging {
  override def request(ctx: CentralPlugged, data: Input): Unit = {
    import data._
    ctx.publicKeys.synchronized {
      logger.info(s"adding public key: ${publicKey.trim}")
      ctx.publicKeys.put(client, publicKey.trim)
      ctx.db.commit()

      val authorizedKeys = root / 'home / 'voicer / ".ssh" / 'authorized_keys
      logger.info(s"regenerating ${authorizedKeys}")
      write.over(
        authorizedKeys,
        ctx
          .publicKeys
          .values()
          .mkString("", "\n", "\n")
      )
    }
  }
}
