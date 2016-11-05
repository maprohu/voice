package voice.audio

import scala.concurrent.duration._
/**
  * Created by maprohu on 05-11-2016.
  */
object RunAudioTools {

  def main(args: Array[String]): Unit = {
    import toolbox8.akka.stream.AkkaStreamTools.Debug._
    import AudioTools.Implicits._

    AudioTools
      .play(
        AudioTools.sine(
          400f,
          2.seconds
        )
      )
  }

}
