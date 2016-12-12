package voice.linux.testing

import org.bridj.Pointer
import voice.linux.jna.c.CLibrary

/**
  * Created by maprohu on 12-12-2016.
  */
object RunTestJNA {

  def main(args: Array[String]): Unit = {
    println(
      CLibrary.if_nametoindex(
        Pointer.pointerToCString("eno1")
      )
    )
    println(
      CLibrary.if_nametoindex(
        Pointer.pointerToCString("lo")
      )
    )
    println(
      CLibrary.if_nametoindex(
        Pointer.pointerToCString("wlo1")
      )
    )

  }

}
