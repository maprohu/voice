package voice.central

import toolbox8.jartree.streamapp.{PlugParams, Plugged, Root}

/**
  * Created by maprohu on 26-11-2016.
  */
class CentralRoot extends Root {
  override def plug(params: PlugParams): Plugged = {
    new CentralPlugged
  }
}
