package voice.testing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source, Tcp}

/**
  * Created by maprohu on 28-10-2016.
  */
object RunRecorder {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem()
    implicit val materiazlier = ActorMaterializer()

    Tcp()
      .outgoingConnection(
        "192.168.1.36",
        21333
      )
      .runWith(
        Source.maybe,
        Sink.foreach({ o =>
          println(o)
        })
      )




  }

}
