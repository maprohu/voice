package voice.rpi.mobile

import java.io.{File, FileInputStream}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.requestapi.RequestMarker
import toolbox8.jartree.streamapp.{PlugParams, Plugged, Root}
import voice.core._

/**
  * Created by maprohu on 21-11-2016.
  */
class VoiceMobileRoot extends Root with StrictLogging {
  override def plug(params: PlugParams): Plugged = {
    logger.info("plugging voice mobile root")
    logger.info(RpiAudio.dumpInfo)
    Thread.currentThread().setContextClassLoader(getClass.getClassLoader)

    val dbDir = new File(params.dir, "db")

    import scala.concurrent.ExecutionContext.Implicits.global
    val cancel = VoiceLogic.run(dbDir)

    new Plugged {
      override def stop(): Unit = {
        cancel.cancel()
      }

      override def marked[In, Out](marker: RequestMarker[In, Out], in: In): Out = ???
    }
  }
}
