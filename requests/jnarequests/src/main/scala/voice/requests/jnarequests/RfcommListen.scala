package voice.requests.jnarequests

import java.io.{DataOutputStream, InputStream, OutputStream, PrintWriter}
import java.nio.IntBuffer

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.common.linux.c.CommonCLibrary
import voice.linux.jna.bluetooth.{BluetoothLibrary, sockaddr_rc}
import voice.linux.jna.c.{CLibrary, sockaddr}

/**
  * Created by pappmar on 13/12/2016.
  */
class RfcommListen extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    Thread.currentThread().setContextClassLoader(
      classOf[CLibrary].getClassLoader
    )

    val c = CLibrary.INSTANCE
    import CLibrary._
    import BluetoothLibrary._
    import c._
    import __socket_type._

    logger.info("reading index")
    val idx = if_nametoindex(
      "eth0"
    )
    logger.info(s"index: ${idx}")

    logger.info("opening rfcomm raw")
    val ctl =
      socket(
        AF_BLUETOOTH,
        SOCK_RAW,
        BTPROTO_RFCOMM
      )

    require(ctl >= 0)

    try {
      logger.info(s"opened: ${ctl}")

      logger.info("opening stream socket")
      val sk =
        socket(
          AF_BLUETOOTH,
          SOCK_STREAM,
          BTPROTO_RFCOMM
        )

      val laddr = new sockaddr_rc()
      laddr.rc_family = AF_BLUETOOTH.toShort
      laddr.rc_channel = 1

      logger.info("binding")
      if (
        CommonCLibrary.INSTANCE.bind(
          sk,
          laddr.getPointer,
          laddr.size()
        ) < 0
      ) {
        perror("binding error")
        throw new Exception
      }

      logger.info("listening")
      listen(
        sk,
        10
      )

      val raddr = new sockaddr_rc()

      logger.info("accepting")
      val nsk = accept(
        sk,
        new sockaddr(raddr.getPointer),
        IntBuffer.wrap(
          Array(
            raddr.size()
          )
        )
      )

      logger.info("getsockname")
      require(
        getsockname(
          nsk,
          new sockaddr(laddr.getPointer),
          IntBuffer.wrap(
            Array(
              laddr.size()
            )
          )
        ) >= 0
      )

      logger.info("closing sk")
      close(sk)
      logger.info("closing nsk")
      close(nsk)


    } finally {
      close(
        ctl
      )

      logger.info("closed")
    }

  }

}
