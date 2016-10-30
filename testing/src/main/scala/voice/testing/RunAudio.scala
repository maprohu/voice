package voice.testing

import toolbox8.akka.stream.AkkaStreamTools
import voice.audio.AudioTools
import scala.concurrent.duration._

/**
  * Created by maprohu on 30-10-2016.
  */
object RunAudio {

  def main(args: Array[String]): Unit = {

    import AkkaStreamTools.Debug._
    import AudioTools.Implicits._


    AudioTools
      .play(
        AudioTools.sine(
          400f,
          100.millis
        )
      )
  }

}
