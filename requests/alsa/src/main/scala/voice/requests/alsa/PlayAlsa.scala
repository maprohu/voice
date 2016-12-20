package voice.requests.alsa

import java.io.{InputStream, OutputStream}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa.AlsaPlayback

/**
  * Created by pappmar on 20/12/2016.
  */
class PlayAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    logger.info("starting alsa playback")
    new AlsaPlayback().start()
  }

}
