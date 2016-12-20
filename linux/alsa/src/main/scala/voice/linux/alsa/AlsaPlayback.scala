package voice.linux.alsa

import com.sun.jna.NativeLong
import com.sun.jna.ptr.{IntByReference, NativeLongByReference, PointerByReference}
import com.typesafe.scalalogging.StrictLogging
import toolbox6.logging.LogTools
import voice.linux.extra.asound.AsoundTools
import voice.linux.common.asound.AsoundLibrary
//import voice.linux.jna.asound.AsoundLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
class AlsaPlayback(
  config: AlsaPlaybackConfig = AlsaPlaybackConfig()
) extends Thread with StrictLogging with LogTools {

  @volatile var stopped = false



  override def run(): Unit = {
    logger.info("alsa playback thread starting")

    quietly {

      val channels = 1

      val libasound = AsoundLibrary.INSTANCE
      import libasound._
      import AsoundLibrary.snd_pcm_stream_t._
      import AsoundLibrary.snd_pcm_access_t._
      import AsoundLibrary.snd_pcm_format_t._
      import AsoundTools._

      val pcm_handle_byref = new PointerByReference

      logger.info(s"opening device:${config.device}")

      alsaSuccess {
        snd_pcm_open(
          pcm_handle_byref,
          config.device,
          SND_PCM_STREAM_PLAYBACK,
          0
        )
      }

      val pcm_handle = pcm_handle_byref.getValue

      logger.info(s"allocating hw params")

      val params = AsoundTools.snd_pcm_hw_params_alloc()

      logger.info(s"hw params any")

      alsaSuccess {
        snd_pcm_hw_params_any(
          pcm_handle,
          params
        )
      }

      logger.info(s"hw params access")
      alsaSuccess {
        snd_pcm_hw_params_set_access(
          pcm_handle,
          params,
          SND_PCM_ACCESS_RW_INTERLEAVED
        )
      }

      logger.info(s"hw params format")
      alsaSuccess {
        snd_pcm_hw_params_set_format(
          pcm_handle,
          params,
          SND_PCM_FORMAT_S16_LE
        )
      }

      logger.info(s"hw params channels")
      alsaSuccess {
        snd_pcm_hw_params_set_channels(
          pcm_handle,
          params,
          channels
        )
      }

      logger.info(s"hw params period")
      alsaSuccess {
        snd_pcm_hw_params_set_period_size(
          pcm_handle,
          params,
          new NativeLong(config.audio.samplesPerPeriod),
          0
        )
      }

      logger.info(s"hw params buffer")
      alsaSuccess {
        snd_pcm_hw_params_set_buffer_size(
          pcm_handle,
          params,
          new NativeLong(config.audio.samplesPerPeriod * config.audio.periodsPerBuffer)
        )
      }

      logger.info(s"hw params rate")
      alsaSuccess {
        snd_pcm_hw_params_set_rate_near(
          pcm_handle,
          params,
          new IntByReference(config.audio.samplesPerSecond),
          new IntByReference(0)
        )
      }

      logger.info(s"hw params")
      alsaSuccess {
        snd_pcm_hw_params(
          pcm_handle,
          params
        )
      }


      logger.info(s"pcm name: ${snd_pcm_name(pcm_handle)}")
      logger.info(s"pcm state: ${snd_pcm_state_name(snd_pcm_state(pcm_handle))}")

      val tmp = new IntByReference()

      snd_pcm_hw_params_get_channels(params, tmp)
      logger.info(s"channels: ${tmp.getValue}")

      snd_pcm_hw_params_get_rate(params, tmp, new IntByReference(0))
      logger.info(s"rate: ${tmp.getValue}")

      val period = new NativeLongByReference()
      snd_pcm_hw_params_get_period_size(
        params,
        period,
        new IntByReference(0)
      )
      logger.info(s"period: ${period.getValue.longValue()}")

      val buffer = new NativeLongByReference()
      snd_pcm_hw_params_get_buffer_size(
        params,
        buffer
      )
      logger.info(s"buffer: ${buffer.getValue.longValue()}")

      val buff_size = period.getValue.longValue() * channels * 2

      logger.info(s"buffer size: ${buff_size}")

      //    snd_pcm_hw_params_get_period_time(
      //      params,
      //      tmp,
      //      null
      //    )
      //
      //    while (!stopped) {
      //
      //
      //    }

    }










  }
}



case class AlsaAudioConfig(
  samplesPerSecond: Int = 44100,
  samplesPerPeriod: Int = 1024 * 4,
  periodsPerBuffer: Int = 2
)

case class AlsaPlaybackConfig(
  device: String = "default",
  audio: AlsaAudioConfig = AlsaAudioConfig()

)
