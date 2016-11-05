package voice.audio

import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
object RunTalker {

  def main(args: Array[String]): Unit = {
    import AudioTools.Implicits._
    import toolbox8.akka.stream.AkkaStreamTools.Debug._

    AudioTools
      .play(
        AudioTools.sine(
          400f,
          100.millis
        )
      )
  }

}
