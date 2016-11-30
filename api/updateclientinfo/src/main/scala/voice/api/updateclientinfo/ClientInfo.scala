package voice.api.updateclientinfo

import toolbox8.jartree.requestapi.RequestMarker

/**
  * Created by pappmar on 28/11/2016.
  */
@SerialVersionUID(1)
case class ClientInfo(
  clientId: Int,
  publicAddress: String,
  localAddresses: Vector[String]
)

object ClientInfo {

  @SerialVersionUID(1)
  case object Update extends RequestMarker[ClientInfo, Unit]

  @SerialVersionUID(1)
  case object Read extends RequestMarker[Int, Option[ClientInfo]]

  @SerialVersionUID(1)
  case object ReadAll extends RequestMarker[Unit, Vector[ClientInfo]]


}



