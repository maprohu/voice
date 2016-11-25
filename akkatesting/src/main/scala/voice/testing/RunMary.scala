package voice.testing

import javax.sound.sampled.{AudioFormat, AudioInputStream, AudioSystem}

import com.jssrc.resample.JSSRCResampler
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
    val out = mary.generateAudio("this is a sample text")

    val conv = new JSSRCResampler(
      out.getFormat,
      mixer.config.audioFormat,
      out
    )



//    val converted = AudioConverterUtils.downSampling(
//      out,
//      mixer.config.audioFormat.getFrameRate.toInt
//    )

//    mixer
//      .sampled(
//        WaveFile.samples(
//          new AudioInputStream(
//
//          )
//          conv
////          out
////          converted
//        )
//      )
//      .play


  }

}
