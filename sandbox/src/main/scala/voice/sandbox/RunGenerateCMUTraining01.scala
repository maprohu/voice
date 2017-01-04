package voice.sandbox

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import javax.sound.sampled._

import scala.io.StdIn
import scala.util.Random

/**
  * Created by pappmar on 04/01/2017.
  */

object CMUTraining01 {

  object Syllable extends Enumeration {
    val

      CSU,
      FI

      = Value
  }


  val Syllables =
    Syllable
      .values
      .toIndexedSeq
}
object RunGenerateCMUTraining01 {

  val speaker1 = "speaker_1"
  val dbName = "csufidb"


  import ammonite.ops._
  val dir = pwd / up / 'voice / 'local / 'cmutrain / 'cmu01
  val textDat = dir / "text.dat"
  val cmuDir = dir / 'cmu
  val wavDir = cmuDir / 'wav
  val etcDir = cmuDir / 'etc
  val speaker1Dir = wavDir / speaker1
  val tmpFile = dir / "tmp.wav"


  def main(args: Array[String]): Unit = {










  }



}

object RunRecordCMUTrainging01 {
  import RunGenerateCMUTraining01._
  import CMUTraining01._
  import CMUTraining._

  def fileName(idx: Int) = {
    s"file_${idx+1}"
  }

  def readMeta = {
    val in = new ObjectInputStream(
      new FileInputStream(
        textDat.toIO
      )
    )
    try {
      in.readObject().asInstanceOf[Seq[Seq[Seq[Syllable.Value]]]]
    } finally {
      in.close()
    }
  }



  def main(args: Array[String]): Unit = {
    val data = readMeta

    import ammonite.ops._
    mkdir(speaker1Dir)

    val info = new DataLine.Info(classOf[TargetDataLine], Format)
    val line = AudioSystem.getLine(info).asInstanceOf[TargetDataLine]

    data
      .zipWithIndex
      .foreach({
        case (lines, idx) =>

          println(s"${idx+1} / ${data.length}")

          val fName = fileName(idx)
          val wavFile = speaker1Dir / s"$fName.wav"

          if (!exists(wavFile)) {
            println(Seq.fill(5)("\n").mkString(""))
            println(s"file: ${wavFile}")
            println()
            println(
              lines
                .map(_.mkString(" "))
                .mkString("\n")
            )

            StdIn.readLine("waiting...")
            line.open(Format)
            line.flush()
            line.start()

            val thread = new Thread() {
              override def run(): Unit = {
                val ais = new AudioInputStream(line)

                AudioSystem.write(
                  ais,
                  AudioFileFormat.Type.WAVE,
                  tmpFile.toIO
                )
              }
            }
            thread.start()

            StdIn.readLine("recording...")
            line.stop()
            line.drain()
            line.close()
            thread.join()

            mv(tmpFile, wavFile)

            println(s"saved ${wavFile}")

          } else {
            println(s"exists: ${wavFile}")
          }

      })

  }

}

object RunGenerateCMUText01 {
  import RunGenerateCMUTraining01._
  import CMUTraining01._

  val MinSyllablesPerLine = 2
  val MaxSyllablesPerLine = 8
  val Lines = 10

  def syllable = {
    Syllables(Random.nextInt(Syllables.length))
  }

//  val RecordingCount = 200
  val RecordingCount = 2

  def main(args: Array[String]): Unit = {
    import ammonite.ops._

    mkdir(dir)
    require(!textDat.toIO.exists())

    val data =
      (1 to RecordingCount) map { recordingIndex =>
        (1 to Lines) map { line =>
          val count = Random.nextInt(MaxSyllablesPerLine - MinSyllablesPerLine) + MinSyllablesPerLine
          (1 to count) map { Syllable =>
            syllable
          }
        }
      }

    val out = new ObjectOutputStream(
      new FileOutputStream(
        textDat.toIO
      )
    )
    out.writeObject(data)
    out.close()
  }

}

object RunGenerateCMUMeta01 {
  import ammonite.ops._
  import RunGenerateCMUTraining01._
  import RunGenerateCMUText01._
  import RunRecordCMUTrainging01._

  val TestingRatio = 2

  def main(args: Array[String]): Unit = {

    val dic = etcDir / s"$dbName.dic"

    write.over(
      dic,
      """CSU CS U
        |FI F I
      """.stripMargin
    )

    val phone = etcDir / s"$dbName.phone"

    write.over(
      phone,
      """CS
        |U
        |F
        |I
        |SIL""".stripMargin
    )

    val fileIds = (0 until RecordingCount)
    val fileNames = fileIds.map(i => s"${speaker1}/${RunRecordCMUTrainging01.fileName(i)}\n")

    val testCount = RecordingCount / TestingRatio
    val trainCount = RecordingCount - testCount

    val trainFileIds = etcDir / s"${dbName}_train.fileids"
    write.over(
      trainFileIds,
      fileNames.take(trainCount).mkString("")
    )

    val testFileIds = etcDir / s"${dbName}_test.fileids"
    write.over(
      testFileIds,
      fileNames.drop(trainCount).mkString("")
    )

    val data = RunRecordCMUTrainging01.readMeta

    val transcripts =
      data
        .zipWithIndex
        .map({
          case (d, idx) =>
            s"<s> ${d.flatten.mkString(" ")} </s> (${fileName(idx)})\n"
        })

    


    val trainTranscript = etcDir / s"${dbName}_train.transcription"
    write.over(
      trainTranscript,
      transcripts.take(trainCount).mkString("")
    )

    val testTranscript = etcDir / s"${dbName}_test.transcription"
    write.over(
      testTranscript,
      transcripts.drop(trainCount).mkString("")
    )

    val filler = etcDir / s"${dbName}.filler"
    write.over(
      filler,
      """<s> SIL
        |</s> SIL
        |<sil> SIL
      """.stripMargin
    )






  }

}
