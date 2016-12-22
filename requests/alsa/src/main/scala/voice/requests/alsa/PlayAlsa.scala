package voice.requests.alsa

import java.io.{InputStream, ObjectInputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder}

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa._

/**
  * Created by pappmar on 20/12/2016.
  */
class PlayAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    val dis = new ObjectInputStream(in)
    val device = dis.readObject().asInstanceOf[String]
    run(device)
  }

  def run(
    device : String = "plughw:1,0",
    freq: Double = 440
  ) = {
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

    runSound(
      device,
      buffered,
      mixer
    )


  }
  def runSound(
    device : String = "plughw:1,0",
    buffered: AlsaBufferedAudioConfig,
    mixer: Sound
  ) = {
    logger.info("starting alsa playback")

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
