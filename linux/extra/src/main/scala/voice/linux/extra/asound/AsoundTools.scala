package voice.linux.extra.asound

/**
  * Created by pappmar on 20/12/2016.
  */
object AsoundTools {
  val libasound = voice.linux.jna.asound.AsoundLibrary.INSTANCE
  import libasound._

  def snd_alloca = {

  }


  def snd_pcm_hw_params_alloca(
  ) = {
    snd_pcm_hw_params_sizeof()

  }

}
