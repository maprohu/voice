package voice.core

import akka.actor.Props
import voice.audio.AudioTools
import voice.core.MixerActor.Play

import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
object RunMixer2 {

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

    mixer ! Play(
      Stream
        .continually(sounds)
        .flatten
        .flatMap({ fn =>
          fn(
            440,
            500.millis
          )
        })
    )

  }

}
