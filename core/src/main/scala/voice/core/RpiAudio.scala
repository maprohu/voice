package voice.core

import javax.sound.sampled._

import com.typesafe.scalalogging.StrictLogging

/**
  * Created by maprohu on 23-11-2016.
  */
object RpiAudio extends StrictLogging {

  val DefaultRate = 44100

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

  def dumpInfo : String = {
    val mixers = AudioSystem.getMixerInfo

    val mixersList =
      mixers
        .map({ mi =>
          val m = AudioSystem.getMixer(mi)
          val s = m.getSourceLineInfo
          val t = m.getTargetLineInfo

          val sourceWithFormats = s
            .map({
              case dl : DataLine.Info =>
                val formats =
                  dl
                    .getFormats
                    .map(f => s"      ${f}")
                    .mkString("\n")

                s"""
                   |    ${dl}
                   |${formats}
                 """.stripMargin

              case dl : Port.Info =>
                s"""
                   |    ${dl}
                 """.stripMargin

            })

          s"""
             |${mi.toString} - ${mi.getName} - ${mi.getDescription}
             |  Source:
             |${sourceWithFormats.mkString("\n")}
             |  Target:
             |${t.map(o => s"    ${o}").mkString("\n")}
             |
           """.stripMargin
        })

    s"""
       |Mixers:
       |${mixers.map(m => s"  ${m}").mkString("\n")}
       |
       |${mixersList.mkString("\n")}
     """.stripMargin
  }


}
