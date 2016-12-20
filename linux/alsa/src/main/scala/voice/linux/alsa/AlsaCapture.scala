package voice.linux.alsa

import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference
import voice.linux.jna.asound.AsoundLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
class AlsaCapture extends Thread {
  override def run(): Unit = {

    val libasound = AsoundLibrary.INSTANCE
    import libasound._

    val pcm_handle = new PointerByReference
    snd_pcm_open(
      pcm_handle

    )


  }
}
