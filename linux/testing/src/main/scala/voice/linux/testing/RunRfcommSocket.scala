package voice.linux.testing

import java.nio.channels.SocketChannel

import voice.linux.common.bluetooth.RFCOMMSocketAddress

/**
  * Created by pappmar on 16/12/2016.
  */
object RunRfcommSocket {

  def main(args: Array[String]): Unit = {

    SocketChannel.open(
      new RFCOMMSocketAddress()
    )


  }

}
