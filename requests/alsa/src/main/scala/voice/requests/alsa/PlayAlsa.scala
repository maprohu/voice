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
    run()
  }

  def run(
    device : String = "default",
    freq: Double = 440
  ) = {
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

    import audio._
    val wavePeriodsPerSecond = freq
    val secondsPerWavePeriod = 1 / wavePeriodsPerSecond
    val samplesPerWavePeriod = secondsPerWavePeriod * samplesPerSecond
    val wavePeriodsPerSample = 1 / samplesPerWavePeriod
    println(samplesPerWavePeriod)

    (0 until audio.samplesPerPeriod).foreach { i =>

      val n = ( Math.sin(2 * Math.PI * wavePeriodsPerSample * i) * (Short.MaxValue / 2 - 1) ).toShort
      println(n)
      shortBuff.put(n)
    }

    val player = new AlsaPlayback(
      AlsaPlaybackConfig(
        device = device,
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
