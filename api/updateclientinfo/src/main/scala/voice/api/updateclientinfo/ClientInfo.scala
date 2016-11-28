package voice.api.updateclientinfo

/**
  * Created by pappmar on 28/11/2016.
  */
case class ClientInfo(
  clientId: Int,
  publicAddress: String,
  localAddress: String
)

object ClientInfo {

  val UPDATE = "voice.central.UpdateClientInfo"

}



