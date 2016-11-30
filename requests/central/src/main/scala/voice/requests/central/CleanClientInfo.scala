package voice.requests.central

import toolbox8.jartree.streamapp.Requestable
import voice.central.CentralPlugged

/**
  * Created by pappmar on 30/11/2016.
  */
class CleanClientInfo extends Requestable[CentralPlugged, Unit, Unit] {
  override def request(ctx: CentralPlugged, data: Unit): Unit = {
    ctx.clientInfo.clear()
    ctx.db.commit()
  }
}
