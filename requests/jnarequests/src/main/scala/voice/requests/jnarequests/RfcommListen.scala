package voice.requests.jnarequests

import java.io.{FileInputStream, InputStream, OutputStream}
import java.nio.IntBuffer

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna.{Memory, Native, Pointer}
import com.typesafe.scalalogging.StrictLogging
import toolbox6.tools.TF
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.common.c.CommonCLibrary
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
      _ = {
        logger.info(s"nsk: ${nsk}")
        logger.info(s"raddr: ${raddr.rc_bdaddr.b.toSeq}")

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

        logger.info(s"laddr: ${laddr.rc_bdaddr.b.toSeq}")

      }

    } {
      logger.info("accepted")

      val c = 'X'.toByte

      logger.info(s"writing: ${c.toChar}")

      val d = new Memory(1)
      d.write(0, Array(c), 0, 1)
      write(
        nsk,
        d,
        new NativeSize(1)
      )

      logger.info("written")


    }


  }

}
