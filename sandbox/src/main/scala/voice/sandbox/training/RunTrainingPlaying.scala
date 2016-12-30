package voice.sandbox.training

import javax.sound.sampled.AudioSystem

import voice.storage.{Consonants, Vowels}
import voice.storage.Syllables.Syllable

import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 30-12-2016.
  */
object RunTrainingPlaying {

  val line =
    AudioSystem.getSourceDataLine(
      RunTrainingRecording.RecordingFormat
    )
  line.open(RunTrainingRecording.RecordingFormat, RunTrainingRecording.BufferSize * 2)
  line.start()

  def play(s: Syllable, id: Long) : Boolean = {
    println(s.string)

    TrainingDB
      .recordingData
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
            .recordingMeta
            .get()

        TrainingDB
          .recordingMeta
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
          .recordingData
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
          .recordingMeta
          .get()
          .data
          .flatMap({
            case (s, ids) =>
              ids.map(id => (s, id))
          })
      )
      .toSeq
      .sortBy(x => -x._2)
      .filter({
        case (s, id) =>
          import Consonants._
          import Vowels._

          s == Syllable(P, I)
      })
      .takeWhile({
        case (s, id) =>
          play(s, id)
      })
      .foreach(_ => ())


  }

}
