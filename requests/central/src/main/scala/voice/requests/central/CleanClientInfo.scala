package voice.requests.central

import java.io.{InputStream, OutputStream}

import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.central.CentralPlugged

/**
  * Created by pappmar on 30/11/2016.
  */
class CleanClientInfo extends Requestable {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    val p =
      ctx
        .holder
        .get
        .plugged
        .asInstanceOf[CentralPlugged]

    p.clientInfo.clear()
    p.db.commit()
  }
}
