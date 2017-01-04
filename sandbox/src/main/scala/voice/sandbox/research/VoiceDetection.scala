package voice.sandbox.research

import java.nio.{ByteBuffer, ByteOrder}

import voice.sandbox.research.DisplayJogl.RenderedLine
import voice.sandbox.training.TrainingDB
import voice.storage.Syllables.Syllable
import voice.storage.{Consonants, Vowels}


/**
  * Created by pappmar on 04/01/2017.
  */
object VoiceDetection {
  import Samples._

  val AutocorrelationWindowSize = 1024 * 4


  def main(args: Array[String]): Unit = {
    import DisplayJogl._
    import voice.storage.Consonants._
    import voice.storage.Vowels._

    val sds =
      data(
        H, UE
      )
      .take(4)

    val ds = sds.map(sd => toDoubles(toShorts(sd)))

    val from = 0
    val to = ds.map(_.length).max

    val lines =
      linesStates(render(ds))

    DisplayJogl.show(
      from,
      to,
      lines
    )

  }

}
