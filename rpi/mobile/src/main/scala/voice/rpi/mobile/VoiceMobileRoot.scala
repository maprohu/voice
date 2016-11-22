package voice.rpi.mobile

import java.io.FileInputStream

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{PlugParams, Plugged, Root}
import voice.core._
import voice.core.events.ControllerEvent

/**
  * Created by maprohu on 21-11-2016.
  */
class VoiceMobileRoot extends Root with StrictLogging {
  override def plug(params: PlugParams): Plugged = {
    logger.info("plugging voice mobile root")

    val cancel = VoiceLogic.run()

    new Plugged {
      override def preUnplug: Any = {
        cancel.cancel()
      }
      override def postUnplug: Unit = ()
    }
  }
}
