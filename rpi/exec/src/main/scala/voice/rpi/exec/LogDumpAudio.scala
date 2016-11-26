package voice.rpi.exec

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Plugged, Requestable}
import voice.core.RpiAudio

/**
  * Created by pappmar on 25/11/2016.
  */
class LogDumpAudio extends Requestable[Plugged, AnyRef, String] with StrictLogging {
  override def request(ctx: Plugged, data: AnyRef): String = {
    val d = RpiAudio.dumpInfo
    logger.info("Audio dump: {}", d)
    d
  }
}
