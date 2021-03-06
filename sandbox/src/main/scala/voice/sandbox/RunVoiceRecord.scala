package voice.sandbox

import java.io.{File, FileOutputStream}
import javax.sound.sampled.{AudioFormat, AudioInputStream, AudioSystem}

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.util.ByteString

import scala.concurrent.Promise
import scala.io.StdIn

/**
  * Created by martonpapp on 04/09/16.
  */
object RunVoiceRecord {

  val VoiceFile = new File("../voice/target/audio.dat")




  def main(args: Array[String]): Unit = {
    import AudioTools._


    val stopper = record(VoiceFile)

//    val out = new FileOutputStream(VoiceFile)
//    val line = AudioSystem.getTargetDataLine(Format)
//
//    line.open(Format)
//    line.start()
//
//    val buffer = Array.ofDim[Byte](line.getBufferSize / 5)

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
//    val finish = Promise[Unit]()
//
//
//    new Thread() {
//      override def run(): Unit = {
//        while (!finish.isCompleted) {
//          val numRead = line.read(buffer, 0, buffer.length)
//          out.write(buffer, 0, numRead)
//          queue.offer(ByteString.fromArray(buffer, 0, numRead))
//        }
//
//        out.close()
//      }
//    }.start()

    StdIn.readLine()

    stopper()

//    finish.success()

//    actorSystem.terminate()




  }


}
