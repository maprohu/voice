package voice.requests.jnarequests

import java.io.{DataOutputStream, InputStream, OutputStream, PrintWriter}

import com.typesafe.scalalogging.StrictLogging
import org.bridj.Pointer
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import voice.linux.jna.bluetooth.BluetoothLibrary
import voice.linux.jna.c.CLibrary

/**
  * Created by pappmar on 13/12/2016.
  */
class RfcommListen extends Requestable with StrictLogging {

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    Thread.currentThread().setContextClassLoader(
      classOf[CLibrary].getClassLoader
    )

    logger.info("reading index")
    val idx = CLibrary.INSTANCE.if_nametoindex(
      "eth0"
    )
    logger.info(s"index: ${idx}")

    logger.info("opening rfcomm raw")
    val ctl = CLibrary.INSTANCE
      .socket(
        CLibrary.AF_BLUETOOTH,
        CLibrary.__socket_type.SOCK_RAW,
        BluetoothLibrary.BTPROTO_RFCOMM
      )

    logger.info(s"opened: ${ctl}")
    require(ctl >= 0)


    CLibrary.INSTANCE
      .close(
        ctl
      )

    logger.info("closed")

  }

}
