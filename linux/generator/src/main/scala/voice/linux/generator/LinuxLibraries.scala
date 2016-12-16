package voice.linux.generator

/**
  * Created by pappmar on 12/12/2016.
  */

class LinuxLibraries(
  libDir: String
) {
  val IncludeDir = "/usr/include"
  val LibIncludeDir = s"${IncludeDir}/${libDir}"

  case class Lib(
    name: String,
    headers: Seq[String]
  )

  val libs = Seq(
    Lib(
      "c",
      Seq(
        s"${IncludeDir}/unistd.h",
        s"${IncludeDir}/string.h",
        s"${IncludeDir}/errno.h",
        s"${IncludeDir}/net/if.h",
//        s"${LibIncludeDir}/sys/cdefs.h",
//        s"${LibIncludeDir}/sys/uio.h",
        s"${LibIncludeDir}/sys/ioctl.h",
        s"${LibIncludeDir}/sys/socket.h",
//        s"${LibIncludeDir}/bits/socket.h",
        s"${LibIncludeDir}/bits/socket_type.h"
      )
    ),
    Lib(
      "bluetooth",
      Seq(
        s"${IncludeDir}/bluetooth/bluetooth.h",
        s"${IncludeDir}/bluetooth/rfcomm.h",
        s"${IncludeDir}/bluetooth/hci.h",
        s"${IncludeDir}/bluetooth/hci_lib.h"
      )
    )
  )

}

object LinuxLibraries {
  val X86 = new LinuxLibraries("x86_64-linux-gnu")
  val Pi = new LinuxLibraries("arm-linux-gnueabihf")
}