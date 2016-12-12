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
        s"${IncludeDir}/net/if.h",
        s"${LibIncludeDir}/sys/cdefs.h",
        s"${LibIncludeDir}/sys/uio.h",
        s"${LibIncludeDir}/sys/socket.h"
      )
    ),
    Lib(
      "bluetooth",
      Seq(
        s"${IncludeDir}/bluetooth/hci.h"
      )
    )
  )

}

object LinuxLibraries {
  val X86 = new LinuxLibraries("x86_64-linux-gnu")
  val Pi = new LinuxLibraries("arm-linux-gnueabihf")
}