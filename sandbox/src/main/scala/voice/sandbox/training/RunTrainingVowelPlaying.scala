package voice.sandbox.training

import javax.sound.sampled.AudioSystem

import voice.storage.Syllables.Syllable
import voice.storage.Vowels

import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 30-12-2016.
  */
object RunTrainingVowelPlaying {

  val line =
    AudioSystem.getSourceDataLine(
      RunTrainingRecording.RecordingFormat
    )
  line.open(RunTrainingRecording.RecordingFormat, RunTrainingRecording.BufferSize * 2)
  line.start()

  def play(s: Vowels.Value, id: Long) : Boolean = {
    println(s)

    TrainingDB
      .vowelRecordingData
      .get(id)
      .grouped(RunTrainingRecording.BufferSize)
      .foreach({ a =>
        line.write(a, 0, a.length)
      })

    val r = StdIn.readLine("waiting")

    r match {
      case "d" =>
        println("deleting")
        val meta =
          TrainingDB
            .vowelRecordingMeta
            .get()

        TrainingDB
          .vowelRecordingMeta
          .set(
            meta
              .copy(
                data =
                  meta
                    .data
                    .updated(
                      s,
                      meta
                        .data(s)
                        .diff(Seq(id))
                    )
              )
          )

        TrainingDB
          .vowelRecordingData
          .remove(id)

        TrainingDB
          .db
          .commit()

      case _ =>
    }

    r != "x"

  }

  def main(args: Array[String]): Unit = {

    Random
      .shuffle(
        TrainingDB
          .vowelRecordingMeta
          .get()
          .data
          .toSeq
          .flatMap({
            case (s, ids) =>
              ids.map(id => (s, id))
          })
      )
      .sortBy(x => x._2)
      .filter({
        case (s, id) =>
          import Vowels._

          s == EE
      })
      .takeWhile({
        case (s, id) =>
          play(s, id)
      })
      .foreach(_ => ())


  }

}
