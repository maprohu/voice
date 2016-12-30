package voice.sandbox.training

import java.io.{ByteArrayOutputStream, FileOutputStream}
import javax.sound.sampled.{AudioFormat, AudioSystem}

import voice.storage.Syllables
import voice.storage.Syllables.Syllable

import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 30-12-2016.
  */
object RunTrainingRecording {

  val SampleRate = 48000
  val BufferSize = 4096
  val RecordingFormat = new AudioFormat(
    SampleRate,
    16,
    1,
    true,
    false
  )

  val IgnoreData = () => ()

  def main(args: Array[String]): Unit = {

    val line = AudioSystem.getTargetDataLine(
      RecordingFormat
    )
    line.open(RecordingFormat, BufferSize * 2)
    line.start()

    val buff = Array.ofDim[Byte](BufferSize)

    @volatile var next : () => Unit = IgnoreData

    val th = new Thread() {
      override def run(): Unit = {
        while (line.read(buff, 0, buff.length) != 0) {
          next()
        }
      }
    }
    th.start()

    val meta =
      TrainingDB
        .recordingMeta
        .get()

    println(
      s"total: ${meta.data.values.map(_.size).sum}"
    )

    val hasRecording =
      meta
        .data
        .filter({
          case (k, v) => v.nonEmpty
        })
        .keys
        .toSet

    val hasNoRecording =
      Syllables
        .Items
        .filterNot(hasRecording.contains)

    println(
      s"no recording (${hasNoRecording.size}): ${hasNoRecording.map(_.string).mkString(", ")}"
    )

    def record(s: Syllable) : Boolean = {
      println(s"${s.consonant} ${s.vowel}")

      (StdIn.readLine("waiting") != "x") && {
        recordNoWait(s)
      }
    }

    def recordNoWait(s: Syllable) : Boolean = {
      val out = new ByteArrayOutputStream()
      next = { () =>
        out.synchronized {
          out.write(buff)
        }
      }

      val r2 = StdIn.readLine("talking")

      next = IgnoreData

      (r2 != "x") && out.synchronized {
        val meta =
          TrainingDB
            .recordingMeta
            .get()

        val rid = meta.nextId

        TrainingDB
          .recordingData
          .put(rid, out.toByteArray)

        val data =
          meta
            .data
            .updated(
              s,
              meta
                .data
                .getOrElse(s, Vector())
                .:+(rid)
            )

        TrainingDB
          .recordingMeta
          .set(
            meta
              .copy(
                nextId = rid + 1,
                data = data
              )

          )

        TrainingDB
          .db
          .commit()

        true
      }

    }

    Random
      .shuffle(
        hasNoRecording
      )
      .takeWhile(record)
      .foreach(_ => ())

    Iterator
      .continually(Syllables.Items(Random.nextInt(Syllables.Items.length)))
      .takeWhile({ s =>
        val ids =
          TrainingDB
            .recordingMeta
            .get()
            .data(s)

        val id = ids(Random.nextInt(ids.length))

        RunTrainingPlaying.play(s, id) && recordNoWait(s)
      })
      .foreach(_ => ())

    line.stop()
    line.close()

  }

}
