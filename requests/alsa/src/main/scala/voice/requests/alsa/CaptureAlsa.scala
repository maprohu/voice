package voice.requests.alsa

import java.io.{InputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder, ShortBuffer}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa._

/**
  * Created by pappmar on 20/12/2016.
  */
class CaptureAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    run()
  }

  def run(
    device : String = "default",
    freq: Double = 440
  ) = {
    logger.info("starting alsa capture")
    val buffered = AlsaBufferedAudioConfig()

    val processor = new SoundProcessor {
      override def next = {}
      override var buffer: ShortBuffer = null
    }

    val capture = new AlsaCapture(
      AlsaCaptureConfig(
        device = device,
        buffered = buffered,
        data = processor
      )
    )

    capture.start()
    logger.info("waiting a little")
    Thread.sleep(1000)
    logger.info("cancelling alsa capture")
    capture.cancel()

    logger.info("waiting for capture thread")
    capture.join()

    logger.info("stopped alsa capture")
  }

}
