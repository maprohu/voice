package voice.testing

import java.io.File

import voice.audio.Talker

/**
  * Created by maprohu on 30-10-2016.
  */
object RunTalker {

  def main(args: Array[String]): Unit = {

    val talker = new Talker(new File("../voice/target/talker-cache"))

    talker.cached("Hello! How are you?")

    println("csuf")
  }

}
