package voice.linux.generator

/**
  * Created by pappmar on 12/12/2016.
  */

object LinuxLibraries {
  case class Lib(
    name: String,
    headers: Seq[String]
  )

  val libs = Seq(
    Lib(
      "c",
      Seq(
        "/usr/include/arm-linux-gnueabihf/sys/cdefs.h",
        "/usr/include/arm-linux-gnueabihf/sys/uio.h",
        "/usr/include/arm-linux-gnueabihf/sys/socket.h"
      )
    ),
    Lib(
      "bluetooth",
      Seq(
        "/usr/include/bluetooth/hci.h"
      )
    )
  )

}
