package voice.core

import javax.sound.sampled.{AudioFormat, AudioSystem, SourceDataLine}

import com.typesafe.scalalogging.StrictLogging

/**
  * Created by maprohu on 23-11-2016.
  */
object RpiAudio extends StrictLogging {

  val MixerName = "Audio [plughw:1,0]"

  val sourceLineFinder : AudioFormat => SourceDataLine = { format =>
    AudioSystem
      .getMixerInfo
      .find(m =>
        m.getName == MixerName ||
          m.getDescription == MixerName
      )
      .map({ found =>
        logger.info("found mixer: {}", found)
        val m = AudioSystem
          .getMixer(
            found
          )

        m.
          getLine(
            m.getSourceLineInfo()(0)
          )
          .asInstanceOf[SourceDataLine]
      })
      .getOrElse({
        logger.info("using default audio line")
        AudioSystem.getSourceDataLine(format)
      })
  }

}
