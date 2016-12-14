package voice.requests.jnarequests

import java.io.{FileInputStream, InputStream, OutputStream}
import java.nio.IntBuffer

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.common.linux.c.CommonCLibrary
import voice.linux.jna.bluetooth.{BluetoothLibrary, bdaddr_t, rfcomm_dev_req, sockaddr_rc}
import voice.linux.jna.c.CLibrary

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
    import voice.linux.common.bluetooth.CommonBluetoothTools._

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

      try {
        val laddr = new sockaddr_rc.ByReference()
        laddr.rc_family = AF_BLUETOOTH.toShort
        laddr.rc_bdaddr = new bdaddr_t(Array.fill[Byte](6)(0))
        laddr.rc_channel = 1

        logger.info("binding")
        if (
          CommonCLibrary.INSTANCE.bind(
            sk,
            laddr,
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

        val raddr = new sockaddr_rc.ByReference()

        logger.info("accepting")
        val nsk = CommonCLibrary.INSTANCE.accept(
          sk,
          raddr,
          IntBuffer.wrap(
            Array(
              raddr.size()
            )
          )
        )

        try {
          logger.info("getsockname")
          require(
            CommonCLibrary.INSTANCE.getsockname(
              nsk,
              laddr,
              IntBuffer.wrap(
                Array(
                  laddr.size()
                )
              )
            ) >= 0
          )

          val dev_id : Short = 0
          val req = new rfcomm_dev_req.ByReference()
          req.dev_id = dev_id
          req.flags = (1 << RFCOMM_REUSE_DLC) | (1 << RFCOMM_RELEASE_ONHUP)
          req.src = laddr.rc_bdaddr.clone()
          req.dst = raddr.rc_bdaddr.clone()
          req.channel = raddr.rc_channel

          val dev = ioctl(
            nsk,
            RFCOMMCREATEDEV,
            req
          )

          require(dev >= 0)

        } finally {
          logger.info("closing nsk")
          close(nsk)
        }

      } finally {
        logger.info("closing sk")
        close(sk)

      }

    } finally {
      close(
        ctl
      )

      logger.info("closed")
    }

  }

}
