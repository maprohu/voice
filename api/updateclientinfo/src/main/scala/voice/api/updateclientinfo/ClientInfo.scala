package voice.api.updateclientinfo

import toolbox8.jartree.requestapi.RequestMarker

/**
  * Created by pappmar on 28/11/2016.
  */
case class ClientInfo(
  clientId: Int,
  publicAddress: String,
  localAddresses: Vector[String]
)

object ClientInfo {

  case object Update extends RequestMarker[ClientInfo, Unit]

//  val UPDATE = "voice.central.UpdateClientInfo"

}



