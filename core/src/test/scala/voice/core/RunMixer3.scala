package voice.core

import akka.actor.Props
import voice.audio.AudioTools
import voice.core.MixerActor.Play

import scala.concurrent.duration._
import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 05-11-2016.
  */
object RunMixer3 {

  def main(args: Array[String]): Unit = {
    import toolbox8.akka.stream.AkkaStreamTools.Debug._

    val mixer = actorSystem.actorOf(Props[MixerActor])

    import AudioTools._

    val sounds =
      Seq(
        sine _,
        square _,
        saw _,
        triangle _
      )

    while (true) {
      val idx =
        Random.nextInt(sounds.size)
      println(idx)

      val sound = sounds(
        idx
      )


      mixer ! Play(
        sound(
          440,
          500.millis
        )
      )

      StdIn.readLine()

    }


  }

}
