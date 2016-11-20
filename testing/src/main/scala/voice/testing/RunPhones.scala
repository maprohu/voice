package voice.testing

import java.io.File

//import voice.audio.Talker
//import voice.core.Phones
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.io.StdIn
//import ammonite.ops._
//import ImplicitWd._
//import voice.core.Phones.Phone
//
///**
//  * Created by maprohu on 01-11-2016.
//  */
//object RunPhones {
//
//  def main(args: Array[String]): Unit = {
//    Phones
//      .toSet
//      .toSeq
//      .sortBy(_.toString())
//      .foreach({ o =>
//        RunPhone.say(o)
//        StdIn.readLine(o.word)
//      })
//  }
//
//}
//
//object RunPhone {
//  val talker = new Talker(
//    new File("../voice/target/talkcache")
//  )
//
//  def say(o: Phone) = {
//    val f = talker.cache(o.word)
//    println(o)
//    %%(
//      "aplay",
//      f.getAbsolutePath
//    )
//  }
//
//
//  def main(args: Array[String]): Unit = {
//    say(
//      Phones.P
//    )
//
//  }
//}
