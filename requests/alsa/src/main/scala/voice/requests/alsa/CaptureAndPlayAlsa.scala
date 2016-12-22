package voice.requests.alsa

import java.io.{InputStream, OutputStream}
import java.nio.{ByteBuffer, ByteOrder, ShortBuffer}
import java.util

import com.sun.jna.Native
import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.alsa._
import voice.linux.common.asound.AsoundLibrary
import voice.linux.common.c.CommonCLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
class CaptureAndPlayAlsa extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    run()

    AsoundLibrary.JNA_NATIVE_LIB.dispose()
    CommonCLibrary.JNA_NATIVE_LIB.dispose()
  }

  def run(
    device : String = "plughw:1,0"
  ) = {
    val buffered = AlsaBufferedAudioConfig()

    val buffers = new CaptureAlsa().runBuffered(
      device,
      buffered
    )

    val sound = new Sound {
      val i = buffers.iterator()

      override def next: Unit = {
        if (i.hasNext) {
          val b = i.next()
          b.rewind()
          buffer.put(b)
        } else {
          while (buffer.hasRemaining) buffer.put(0.toShort)
        }
      }
    }

    new PlayAlsa().runSound(
      device,
      buffered,
      sound
    )

  }

}
