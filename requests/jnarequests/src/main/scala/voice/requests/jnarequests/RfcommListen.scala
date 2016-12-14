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
    import voice.linux.common.bluetooth.CommonBluetoothTools._

    import BTCommon._

    val laddr = new sockaddr_rc.ByReference()
    laddr.rc_family = AF_BLUETOOTH.toShort
    laddr.rc_bdaddr = new bdaddr_t(Array.fill[Byte](6)(0))
    laddr.rc_channel = 1

    val raddr = new sockaddr_rc()

    for {
      ctl <- rfcommRaw
      sk <- rfcommStream
      nsk <- {
        logger.info(s"sk: ${sk}")
        ensureSuccess(
          CommonCLibrary.INSTANCE.bind(
            sk,
            laddr,
            laddr.size()
          )
        )

        ensureSuccess(
          listen(
            sk,
            10
          )
        )

        logger.info("accepting")
        tfSocket(
         CommonCLibrary.INSTANCE.accept(
            sk,
            raddr,
            IntBuffer.wrap(
              Array(
                raddr.size()
              )
            )
          )
        )
      }
      dev = {
        ensureSuccess(
          CommonCLibrary.INSTANCE.getsockname(
            nsk,
            laddr,
            IntBuffer.wrap(
              Array(
                laddr.size()
              )
            )
          )
        )

        val dev_id : Short = 0
        val req = new rfcomm_dev_req()
        req.dev_id = dev_id
        req.flags = (1 << RFCOMM_REUSE_DLC) | (1 << RFCOMM_RELEASE_ONHUP)
        req.src = laddr.rc_bdaddr.clone()
        req.dst = raddr.rc_bdaddr.clone()
        req.channel = raddr.rc_channel

        ioctl(
          nsk,
          RFCOMMCREATEDEV,
          req
        )

      }

    } {
      logger.info("accepted")
      // probably raw
      // open dev

    }


  }

}
