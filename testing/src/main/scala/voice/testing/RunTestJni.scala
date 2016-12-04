package voice.testing

import java.io.File

import voice.jni.{SWIGTYPE_nl_cb_kind, libnl, nl_cb_kind}

/**
  * Created by maprohu on 04-12-2016.
  */
object RunTestJni {

  def main(args: Array[String]): Unit = {
//    System.load("/lib/x86_64-linux-gnu/libnl-3.so")
//    System.load("/lib/x86_64-linux-gnu/libnl-genl-3.so")
    System.load(new File(RunJni.targetso).getAbsolutePath)

    import libnl._
    val if_index = if_nametoindex("wlo1")
    val socket = nl_socket_alloc()
    genl_connect(socket)
    val driver_id = genl_ctrl_resolve(socket, "nl80211")
    val msg = nlmsg_alloc()
    val cb = nl_cb_alloc(nl_cb_kind.NL_CB_DEFAULT)
    val ctrlid = genl_ctrl_resolve(socket, "nlctrl")
    genlmsg_put()



  }

}
