package voice.testing

import akka.actor.Props
import toolbox8.akka.actor.DumpActor
import voice.akka.MixerActor
import voice.akka.MixerActor.Play
import voice.audio.AudioTools

import scala.concurrent.duration._
import scala.io.StdIn
import scala.util.Random

/**
  * Created by maprohu on 05-11-2016.
  */
object RunMixer5 {

  val MinFrequency = 100f
  val MaxFrequency = 10000f
  val BaseFrequency = 440f

  val Step = Math.sqrt(2f).toFloat

  val Frequencies = {
    val lowest =
      Stream
        .iterate(BaseFrequency)(_ / Step)
        .dropWhile(_ / Step > MinFrequency)
        .head

    Stream
      .iterate(lowest)(_ * Step)
      .takeWhile(_ < MaxFrequency)
      .toIndexedSeq
  }

  def main(args: Array[String]): Unit = {
    println(Frequencies.size)

    import toolbox8.akka.stream.AkkaStreamTools.Debug._

    val mixer = actorSystem.actorOf(
      Props(
        classOf[MixerActor],
        MixerActor.Config(
          actorSystem.actorOf(
            Props(
              classOf[DumpActor]
            )
          )
        )
      )
    )

    import AudioTools._


    while (true) {
      val idx =
        Random.nextInt(Frequencies.size)
      val frequency = Frequencies(
        idx
      )
      println(frequency)

      val freqs =
        Stream
          .from(1)
          .map(_ * frequency)
          .takeWhile(_ < MaxFrequency)
          .toIndexedSeq


      println(freqs)



      mixer ! Play(
        freqs
          .map({ f =>
            sine(
              f,
              500.millis
            )
          })
          .reduce({ (a, b) =>
            a.zip(b).map({ case (x, y) => x + y })
          })
          .map(_ / freqs.size)
      )

      StdIn.readLine()

    }


  }

}
