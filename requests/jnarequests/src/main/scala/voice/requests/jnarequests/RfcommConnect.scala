package voice.requests.jnarequests

import java.io.{InputStream, ObjectInputStream, OutputStream}
import java.nio.IntBuffer

import com.typesafe.scalalogging.StrictLogging
import toolbox6.tools.TF
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.common.linux.c.CommonCLibrary
import voice.linux.jna.bluetooth.{BluetoothLibrary, bdaddr_t, rfcomm_dev_req, sockaddr_rc}
import voice.linux.jna.c.CLibrary

/**
  * Created by pappmar on 13/12/2016.
  */

object RfcommConnect {
  case class Input(
    target: String,
    dev_id : Short = 0
  )
}

class RfcommConnect extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    val dis = new ObjectInputStream(in)
    val input = dis.readObject().asInstanceOf[RfcommConnect.Input]
    import input._

    val c = CLibrary.INSTANCE
    val bt = BluetoothLibrary.INSTANCE
    import BluetoothLibrary._
    import CLibrary._
    import __socket_type._
    import c._
    import bt._
    import voice.linux.common.bluetooth.CommonBluetoothTools._

    val laddr = new sockaddr_rc.ByReference()
    laddr.rc_family = AF_BLUETOOTH.toShort
    laddr.rc_bdaddr = new bdaddr_t(Array.fill[Byte](6)(0))
    laddr.rc_channel = 0



    val raddr = new sockaddr_rc()
    raddr.rc_family = AF_BLUETOOTH.toShort
    raddr.rc_bdaddr = new bdaddr_t()
    raddr.rc_channel = 1

    str2ba(
      target,
      raddr.rc_bdaddr
    )

    import BTCommon._
    for {
      ctl <- rfcommRaw
      sk <- rfcommStream
      dev <- TF.from({
        logger.info(s"sk: ${sk}")
        logger.info("binding")
        ensureSuccess(
          CommonCLibrary.INSTANCE.bind(
            sk,
            laddr,
            laddr.size()
          )
        )
        logger.info("connecting")
        ensureSuccess(
          CommonCLibrary.INSTANCE.connect(
            sk,
            raddr,
            raddr.size()
          )
        )
        logger.info("getsockname")
        ensureSuccess(
          CommonCLibrary.INSTANCE.getsockname(
            sk,
            laddr,
            IntBuffer.wrap(Array(laddr.size()))
          )
        )

        val req = new rfcomm_dev_req()
        req.dev_id = dev_id
        req.flags = (1 << RFCOMM_REUSE_DLC) | (1 << RFCOMM_RELEASE_ONHUP)
        req.src = laddr.rc_bdaddr.clone()
        req.dst = raddr.rc_bdaddr.clone()
        req.channel = raddr.rc_channel

        logger.info("create dev")
        ensureSuccess(
          ioctl(
            sk,
            RFCOMMCREATEDEV,
            req
          )
        )


        // probably raw stuff is needed

      })({ dev =>
        val req = new rfcomm_dev_req()
        req.dev_id = dev_id
        req.flags = (1 << RFCOMM_HANGUP_NOW)
        ioctl(
          ctl,
          RFCOMMRELEASEDEV,
          req
        )
      })

    } {
      logger.info("connected")
      // open /dev/rfcomm0
      // write stuff

    }




  }

}
