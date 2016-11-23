package voice.testing

import java.io.File

import toolbox8.leveldb.LevelDB
import voice.core.SingleRecorder.Config
import voice.core.{SingleMixer, TextGenerator, TextReader}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by maprohu on 23-11-2016.
  */
object RunTextReader {

  def main(args: Array[String]): Unit = {

    val db = LevelDB(new File("../voice/target/leveldbtext"))
    val mixer = SingleMixer(SingleMixer.Config(samplesPerSecond = 48000f))
    val reader = new TextReader(db, mixer)

    reader
      .read("It seems like resampling is not a good idea.")
      .onComplete(println)



  }

}
