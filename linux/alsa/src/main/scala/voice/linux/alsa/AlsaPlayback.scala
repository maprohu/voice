package voice.linux.alsa

import java.nio.{Buffer, ByteBuffer, ByteOrder, ShortBuffer}

import com.sun.jna.{Native, NativeLong, Pointer}
import com.sun.jna.ptr.{IntByReference, NativeLongByReference, PointerByReference}
import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable
import toolbox6.logging.LogTools
import voice.linux.extra.asound.AsoundTools
import voice.linux.common.asound.AsoundLibrary
//import voice.linux.jna.asound.AsoundLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
class AlsaPlayback(
  config: AlsaPlaybackConfig
) extends Thread with StrictLogging with LogTools with Cancelable {
  import config._
  import buffered._
  import periods._
  import sampled._

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

//      val params = AsoundTools.snd_pcm_hw_params_alloc()
      val paramsBuffer = ByteBuffer.allocateDirect(
        snd_pcm_hw_params_sizeof().intValue()
      )
      val params = Native.getDirectBufferPointer(paramsBuffer)


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
          new NativeLong(samplesPerPeriod),
          0
        )
      }

      logger.info(s"hw params buffer")
      alsaSuccess {
        snd_pcm_hw_params_set_buffer_size(
          pcm_handle,
          params,
          new NativeLong(samplesPerPeriod * periodsPerBuffer)
        )
      }

//      logger.info(s"hw params resample")
//      alsaSuccess {
//        snd_pcm_hw_params_set_rate_resample(
//          pcm_handle,
//          params,
//          1
//        )
//      }

      logger.info(s"hw params rate")
      alsaSuccess {
        snd_pcm_hw_params_set_rate_near(
          pcm_handle,
          params,
          new IntByReference(samplesPerSecond),
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



      val writeSize = new NativeLong(
        samplesPerPeriod
      )

      logger.info(s"start playing")

      val samplesPerWrite = samplesPerPeriod

      val byteBuffer = ByteBuffer.allocateDirect(
        config.buffered.periods.samplesPerPeriod * java.lang.Short.BYTES
      )
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
      val shortBuffer = byteBuffer.asShortBuffer()
      val data = config.data
      data.buffer = shortBuffer

      try {
//        while (!stopped) {
//          shortBuffer.rewind()
//          data.next
//
//
//          val written = snd_pcm_writei(
//            pcm_handle,
//            byteBuffer,
//            writeSize
//          )
//
//          require(written.intValue() == samplesPerWrite)
//        }

        logger.info(s"drain")
        snd_pcm_drain(pcm_handle)

      } finally {
        logger.info(s"close")
        snd_pcm_close(pcm_handle)
      }

    }

    logger.info(s"alsa playback thread exiting")
  }

  override def cancel(): Unit = {
    stopped = true
  }
}



case class AlsaSampledAudioConfig(
  samplesPerSecond: Sounds.SamplesPerSecond = 48000
) {
}

case class AlsaPeriodsAudioConfig(
  sampled: AlsaSampledAudioConfig = AlsaSampledAudioConfig(),
  samplesPerPeriod: Int = 1024 * 4
) {
  def bytesPerPeriod = samplesPerPeriod * java.lang.Short.BYTES
}

case class AlsaBufferedAudioConfig(
  periods: AlsaPeriodsAudioConfig = AlsaPeriodsAudioConfig(),
  periodsPerBuffer: Int = 2
)

case class AlsaPlaybackConfig(
  device: String = "default",
  buffered: AlsaBufferedAudioConfig,
  data: Sound
)

