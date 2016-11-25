package voice.testing

import java.io.File

import voice.audio.Talker

import scala.io.StdIn

/**
  * Created by maprohu on 30-10-2016.
  */
object RunTalker {

  def main(args: Array[String]): Unit = {
    import monix.execution.Scheduler.Implicits.global

    val talker = new Talker(new File("../voice/target/talker-cache"))

//    Talker.play(talker.cache("hello"))

    talker.cached("choof parashounak")
//    talker.cached("Hello! How are you?")

    println("csuf")

    StdIn.readLine()
//
//    talker.cached("Hello! How are you?")
//
//    StdIn.readLine()
  }

}
