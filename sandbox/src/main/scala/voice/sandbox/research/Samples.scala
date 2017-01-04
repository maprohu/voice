package voice.sandbox.research

import java.nio.{ByteBuffer, ByteOrder}

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color._
import voice.sandbox.research.DisplayJogl.{LineState, RenderedLine}
import voice.sandbox.training.TrainingDB
import voice.storage.Syllables.Syllable
import voice.storage.{Consonants, Vowels}


/**
  * Created by pappmar on 04/01/2017.
  */
object Samples {

  type SampleData = Array[Byte]
  type SampleDatas = Seq[SampleData]

  def data(
    c: Consonants.Value,
    v: Vowels.Value
  ) : SampleDatas = {
    val s = Syllable(c, v)

    TrainingDB
      .recordingMeta
      .get()
      .data
      .get(s)
      .toSeq
      .flatMap({ ids =>
        ids
          .map({ id =>
            TrainingDB
              .recordingData
              .get(id)
          })
      })
  }

  def data(s: Vowels.Value) : SampleDatas = {
    TrainingDB
      .vowelRecordingMeta
      .get()
      .data
      .get(s)
      .toSeq
      .flatMap({ ids =>
        ids
          .map({ id =>
            TrainingDB
              .vowelRecordingData
              .get(id)
          })
      })
  }

  def toShorts(
    byteArray: SampleData
  ) : Array[Short] = {
    val sb = ByteBuffer
      .wrap(
        byteArray
      )
      .order(ByteOrder.LITTLE_ENDIAN)
      .asShortBuffer()

    val a = Array.ofDim[Short](sb.capacity())
    sb.get(a)
    a
  }

  def toDoubles(
    shorts: Array[Short]
  ) : Array[Double] = {
    shorts
      .map({ s =>
        s / -(Short.MinValue.toDouble)
      })
  }

  def render(
    sampleData: Array[Double]
  ) : RenderedLine = {
    sampleData

      .zipWithIndex
      .map({ case (y, idx) =>
        (idx.toDouble, y)
      })
      .toVector
  }

  def render(
    samplesData: Seq[Array[Double]]
  ) : Seq[RenderedLine] = {
    samplesData
      .map({ sd =>
        render(sd)
      })
  }


  def main(args: Array[String]): Unit = {
    import voice.storage.Consonants._
    import voice.storage.Vowels._
    import DisplayJogl._



    val sds =
      data(
        F, UE
      )
      .take(4)

    val ds = sds.map(sd => toDoubles(toShorts(sd)))

    val from = 0
    val to = ds.map(_.length).max

    val lines =
      render(ds)
        .zipWithIndex
        .map({
          case (l, idx) =>
            new LineState(
              l,
              color(idx)
            )
        })

    DisplayJogl.show(
      from,
      to,
      lines
    )

  }

}
