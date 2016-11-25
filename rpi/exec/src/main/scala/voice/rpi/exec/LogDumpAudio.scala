package voice.rpi.exec

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.Requestable
import voice.core.RpiAudio

/**
  * Created by pappmar on 25/11/2016.
  */
class LogDumpAudio extends Requestable with StrictLogging {
  override def request(data: AnyRef): AnyRef = {
    val d = RpiAudio.dumpInfo
    logger.info("Audio dump: {}", d)
    d
  }
}
