package voice.requests.alsa

import java.io.{InputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder, ShortBuffer}
import java.util

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

    val buffers = new util.LinkedList[ShortBuffer]()

    val processor = new SoundProcessor {

      override def buffer: ShortBuffer = {
        val byteBuffer = ByteBuffer.allocateDirect(
          buffered.periods.bytesPerPeriod
        )
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        val shortBuffer = byteBuffer.asShortBuffer()
        buffers.add(shortBuffer)
        shortBuffer
      }

      override def next = {}
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
    Thread.sleep(3000)
    logger.info("cancelling alsa capture")
    capture.cancel()

    logger.info("waiting for capture thread")
    capture.join()

    logger.info("stopped alsa capture")

    val sound = new Sound {
      val i = buffers.iterator()

      override def next: Unit = {
        if (i.hasNext) {
          val b = i.next()
          b.rewind()
          buffer.put(b)
        } else {
          while (buffer.hasRemaining) buffer.put(0.toShort)
        }
      }
    }

    val player = new AlsaPlayback(
      AlsaPlaybackConfig(
        device,
        buffered,
        sound
      )
    )

    player.start()
    logger.info("waiting a little")
    Thread.sleep(3000)
    logger.info("cancelling alsa playback")
    player.cancel()

    logger.info("waiting for player thread")
    player.join()

    logger.info("stopped alsa playback")
  }

}
