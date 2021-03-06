package voice.testing

import akka.actor.Props
import voice.akka.MixerActor
import voice.akka.MixerActor.Play
import voice.audio.AudioTools

import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
object RunMixer {

  def main(args: Array[String]): Unit = {
    import toolbox8.akka.stream.AkkaStreamTools.Debug._

    val mixer = actorSystem.actorOf(Props[MixerActor])

    mixer ! Play(
      AudioTools
        .sine(
          880,
          1.second
        )
    )
    mixer ! Play(
      AudioTools
        .sine(
          440,
          1.second
        )
    )

    actorSystem
      .scheduler
      .scheduleOnce(
        2.seconds,
        mixer,
        Play(
          AudioTools
            .sine(
              880,
              1.second
            )
        )
      )


  }

}
