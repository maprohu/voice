package voice.requests.common

import toolbox8.jartree.streamapp.{Plugged, Requestable}

/**
  * Created by maprohu on 29-11-2016.
  */
class QueryPlugged extends Requestable[Plugged, Unit, String] {
  override def request(ctx: Plugged, data: Unit): String = {
    s"${ctx} - ${ctx.getClass.getPackage.getImplementationVersion()}"
  }
}
