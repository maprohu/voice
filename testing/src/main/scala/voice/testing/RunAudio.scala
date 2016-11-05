package voice.testing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import toolbox8.akka.stream.AkkaStreamTools
import voice.audio.AudioTools

import scala.concurrent.duration._
import scala.io.StdIn

/**
  * Created by maprohu on 30-10-2016.
  */
object RunAudio {

  def main(args: Array[String]): Unit = {

//    import AkkaStreamTools.Debug._
    implicit val m = AkkaStreamTools.Debug.materializer
//    import AudioTools.Implicits._

    StdIn.readLine()

    val s =
    Source
      .repeat("x")

    StdIn.readLine()

    s
      .runForeach(println)

//    AudioTools
//      .play(
//        AudioTools.sine(
//          400f,
//          100.millis
//        )
//      )

    StdIn.readLine()
  }

}
