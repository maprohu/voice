package voice.core

import voice.core.SingleMixer.SoundForm

/**
  * Created by maprohu on 19-11-2016.
  */
object RunPlaySine {

  def main(args: Array[String]): Unit = {

    val mixer = SingleMixer()

    mixer
      .render(
        SoundForm(
          seconds = 10,
          form = { t =>
            math.sin(2 * math.Pi * 440 * t).toFloat
          }
        )
      )
      .play()
  }

}
