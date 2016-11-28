package voice.central

import toolbox8.jartree.streamapp.Requestable
import voice.api.updateclientinfo.ClientInfo

/**
  * Created by pappmar on 28/11/2016.
  */
class UpdateClientInfo extends Requestable[CentralPlugged, ClientInfo, Unit]  {
  override def request(ctx: CentralPlugged, data: ClientInfo): Unit = {
    ctx.clientInfo.put(data.clientId, data)
    ctx.db.commit()
  }
}
