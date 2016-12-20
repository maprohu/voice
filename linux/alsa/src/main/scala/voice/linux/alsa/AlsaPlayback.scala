package voice.linux.alsa

import com.sun.jna.ptr.PointerByReference
import voice.linux.jna.asound.AsoundLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
class AlsaPlayback(
  config: AlsaPlaybackConfig
) extends Thread {

  override def run(): Unit = {

    val libasound = AsoundLibrary.INSTANCE
    import libasound._
    import AsoundLibrary.snd_pcm_stream_t._
    import voice.linux.extra.NativeTools._

    val pcm_handle = new PointerByReference


    val pcm = ensureSuccess {
      snd_pcm_open(
        pcm_handle,
        config.device,
        SND_PCM_STREAM_PLAYBACK,
        0
      )
    }





  }
}

case class AlsaPlaybackConfig(
  device: String

)
