package voice.requests.jnarequests

import toolbox6.tools.TF
import voice.linux.jna.bluetooth.BluetoothLibrary._
import voice.linux.jna.c.CLibrary
import voice.linux.jna.c.CLibrary._
import voice.linux.jna.c.CLibrary.__socket_type._

/**
  * Created by pappmar on 14/12/2016.
  */
object BTCommon {

  def tfSocket(
    open: => Int
  ) : TF[Int] = {
    TF.from({
      ensureSuccess(open)
    })(
      id => CLibrary.INSTANCE.close(id)
    )
  }

  def rfcommRaw = {
    tfSocket(
      CLibrary.INSTANCE.socket(
        AF_BLUETOOTH,
        SOCK_RAW,
        BTPROTO_RFCOMM
      )
    )
  }

  def rfcommStream = {
    tfSocket(
      CLibrary.INSTANCE.socket(
        AF_BLUETOOTH,
        SOCK_STREAM,
        BTPROTO_RFCOMM
      )
    )
  }

  def ensureSuccess(what: => Int) : Int = {
    val res = what
    require(res >= 0)
    res
  }

}
