package voice.testing

import javax.sound.sampled.{AudioFormat, AudioSystem}

import marytts.LocalMaryInterface
import marytts.util.data.audio.AudioConverterUtils
import voice.core.{SingleMixer, WaveFile}

/**
  * Created by pappmar on 23/11/2016.
  */
object RunMary {

  def main(args: Array[String]): Unit = {
    val mixer = SingleMixer()
    val mary = new LocalMaryInterface
    val out = mary.generateAudio("boo")
    val converted = AudioConverterUtils.downSampling(
      out,
      mixer.audioFormat.getFrameRate.toInt
    )

    mixer
      .sampled(
        WaveFile.samples(
          converted
        )
      )
      .play


  }

}
