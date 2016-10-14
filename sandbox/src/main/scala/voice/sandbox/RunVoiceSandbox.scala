package voice.sandbox

import javax.sound.sampled.{AudioFormat, AudioSystem}

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}
import akka.util.ByteString
import com.sun.media.sound.FFT

import scala.util.Random

/**
  * Created by martonpapp on 04/09/16.
  */
object RunVoiceSandbox {


//  def main(args: Array[String]): Unit = {
//    val format = new AudioFormat(44100.0f, 16, 1, true, true)
//    val line = AudioSystem.getTargetDataLine(format)
//
//    line.open(format)
//    line.start()
//
//    val buffer = Array.ofDim[Byte](line.getBufferSize / 5)
//
//    implicit val actorSystem = ActorSystem()
//    implicit val materializer = ActorMaterializer()
//
//    val queue =
//      Source
//        .queue[ByteString](16, OverflowStrategy.fail)
//        .via(Streams.groupBytes(
//          line.getFormat.getFrameSize
//        ))
//        .map({ bs =>
//          val ba = bs.toArray
//
//          (ba(0).toInt << 8) | (ba(1).toInt & 0xFF)
//        })
//        .to(Sink.foreach(println))
//        .run()
//
//    while (true) {
//      val numRead = line.read(buffer, 0, buffer.length)
//      queue.offer(ByteString.fromArray(buffer, 0, numRead))
//    }
//
//
//
//
//  }


}
