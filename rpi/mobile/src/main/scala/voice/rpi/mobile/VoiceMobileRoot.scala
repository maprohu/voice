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

    val deviceFile =
      HidParser.DevPath.toFile

    val cancel =
      HidPhysicalThread.run({ () =>
        logger.info("starting hid reading")

        while (!deviceFile.exists()) {
          logger.info("device file not present, waiting...")
          Thread.sleep(1000)
        }

        val is = new FileInputStream(
          deviceFile
        )

        val p =
          new HidSingleProcessor(
            new HidLongProcessor(
              new ShortLongProcessor {
                override def onError(ex: Throwable): Unit = ()

                override def onComplete(): Unit = ()

                override def onLong(e: ControllerEvent): Unit = ()

                override def onShort(e: ControllerEvent): Unit = ()

                override def onDown(e: ControllerEvent): Unit = {
                  logger.info(s"down: ${e}")
                }
              }

            )

          )

        (is, p)
      })



    new Plugged {
      override def preUnplug: Any = {
        cancel.cancel()
      }
      override def postUnplug: Unit = ()
    }
  }
}
