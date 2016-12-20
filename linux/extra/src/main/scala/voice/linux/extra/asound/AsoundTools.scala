package voice.linux.extra.asound

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna.Memory
import voice.linux.common.asound.AsoundLibrary
import voice.linux.extra.NativeTools
//import voice.linux.jna.asound.AsoundLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
object AsoundTools {
  val libasound = AsoundLibrary.INSTANCE
  import libasound._

  def snd_pcm_hw_params_alloc() = {
    NativeTools.alloc(
      snd_pcm_hw_params_sizeof()
    )
  }

  def alsaSuccess(what: => Int) : Int = {
    val res = what
    if (res < 0) {
      throw new Exception(
        s"alsa failure: ${
          synchronized { AsoundLibrary.INSTANCE.snd_strerror(res) }
        }"
      )
    }
    res
  }

}
