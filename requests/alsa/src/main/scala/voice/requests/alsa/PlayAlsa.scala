package voice.requests.alsa

import java.io.{InputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa._

/**
  * Created by pappmar on 20/12/2016.
  */
class PlayAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    run()
  }

  def run(
    device : String = "default",
    freq: Double = 440
  ) = {
    logger.info("starting alsa playback")
    val buffered = AlsaBufferedAudioConfig()

    val mixer = new MixerSound(buffered.periods)

    mixer
      .play(
        Sounds.repeatInfinitely(
          Sounds.singleWavePeriod(
            freq,
            buffered.periods.sampled
          ),
          buffered.periods
        )
      )

    val player = new AlsaPlayback(
      AlsaPlaybackConfig(
        device = device,
        buffered = buffered,
        data = mixer
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
