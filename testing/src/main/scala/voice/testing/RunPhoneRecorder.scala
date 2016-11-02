package voice.testing

import java.io.File

import toolbox6.leveldb.LevelDB
import voice.tools.PhoneRecorder

/**
  * Created by maprohu on 02-11-2016.
  */
object RunPhoneRecorder {

  def main(args: Array[String]): Unit = {
    val db = LevelDB(new File("../voice/phonedb"))

    PhoneRecorder
      .run(
        db
      )
  }

}
