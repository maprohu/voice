package voice.requests.alsa

import java.io.{InputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa.{AlsaAudioConfig, AlsaPlayback, AlsaPlaybackConfig}

/**
  * Created by pappmar on 20/12/2016.
  */
class PlayAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    logger.info("starting alsa playback")
    val audio = AlsaAudioConfig()

    val buff =
      ByteBuffer
        .allocateDirect(
          audio.bytesPerPeriod
        )
    buff.order(ByteOrder.LITTLE_ENDIAN)

    val shortBuff =
      buff.asShortBuffer()

    (0 until audio.samplesPerPeriod).foreach { _ =>
      shortBuff.put(0.toShort)
    }

    val player = new AlsaPlayback(
      AlsaPlaybackConfig(
        audio = audio,
        data = () => buff
      )
    )

    player.start()
    logger.info("waiting a little")
    Thread.sleep(1000)
    logger.info("cancelling alsa playback")
    player.cancel()

    logger.info("waiting for player thread")
    player.join()

    logger.info("stopped alsa playback")
  }

}
