package voice.linux.testing

import com.sun.jna.Pointer
import voice.linux.jna.bluetooth.BluetoothLibrary
import voice.linux.jna.c.CLibrary

/**
  * Created by maprohu on 12-12-2016.
  */
object RunTestJNA {

  def main(args: Array[String]): Unit = {
    println(
      CLibrary.INSTANCE.if_nametoindex(
        "eno1"
      )
    )

    BluetoothLibrary.INSTANCE.hci_open_dev(0)



  }

}
