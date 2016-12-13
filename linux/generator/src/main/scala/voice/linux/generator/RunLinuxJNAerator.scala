package voice.linux.generator

import java.io.File

import com.ochafik.lang.jnaerator.JNAerator
import sbt.io.IO

/**
  * Created by pappmar on 12/12/2016.
  */
object RunLinuxJNAerator {

  val TargetMavenDir = "../voice/linux/jnalib"
  val TargetSourceDir = s"${TargetMavenDir}/src/main/java"

  def main(args: Array[String]): Unit = {
    run(
      LinuxLibraries.Pi,
      RunLinuxDonwloadHeaders.TargetDirPath
    )
  }

  def run(
    libs: LinuxLibraries,
    targetDir: String
  ) = {
    val tf = new File(TargetMavenDir)
    val targetSourceDir = new File(TargetSourceDir)

    IO.delete(targetSourceDir)
    targetSourceDir.mkdirs()

    val skipFunctions = Seq(
      "__bswap_16",
      "__bswap_32",
      "__bswap_64",
      "_IO_cookie_init",
      "crypt",
      "encrypt"
    )

    JNAerator
      .main(
        Array[String](
          "-runtime", "JNAerator",
//          "-runtime", "JNA",
          "-noLibBundle",
          "-mavenGroupId", "voice",
          "-mavenArtifactId", "voice-linux-jnalib",
          "-mavenVersion", "2-SNAPSHOT",
          "-D_GNU_SOURCE=1",
          "-D__ARM_PCS_VFP=1",
          "-skipFunctions", skipFunctions.mkString("|"),
          "-I", s"${targetDir}/usr/lib/gcc/arm-linux-gnueabihf/4.9.2/include",
          "-I", s"${targetDir}${libs.IncludeDir}",
          "-I", s"${targetDir}${libs.IncludeDir}/linux",
          "-I", s"${targetDir}${libs.LibIncludeDir}",
          "-o", TargetSourceDir,
          "-mode", "Directory",
//          "-o", TargetMavenDir,
//          "-mode", "Maven",
          "-rootPackage", "voice.linux.jna"
        ) ++
        libs
          .libs
          .flatMap({ lib =>
            Seq(
              "-library", lib.name
            ) ++
              lib
                .headers
                .map({ f =>
                  new File(targetDir, f.tail)
                })
//                .filter(_.exists())
                .map(_.toString)
          })
      )
  }

}

object RunJNALocal {
  def main(args: Array[String]): Unit = {
    RunLinuxJNAerator.run(
      LinuxLibraries.X86,
      ""
    )
  }
}

object RunJNALocalPi {
  def main(args: Array[String]): Unit = {
    RunLinuxJNAerator.run(
      LinuxLibraries.Pi,
      "../voice/local/headers"
    )
  }
}

/*


--- libio.h
  /* Make sure we don't get into trouble again.  */
//  char _unused2[15 * sizeof (int) - 4 * sizeof (void *) - sizeof (size_t)];


--- bluetooth.h
//#ifndef AF_BLUETOOTH
#define AF_BLUETOOTH	31
#define PF_BLUETOOTH	AF_BLUETOOTH
//#endif

/* Bluetooth unaligned access */
#define bt_get_unaligned(ptr)			0
//#define bt_get_unaligned(ptr)			\
//({						\
//	struct __attribute__((packed)) {	\
//		__typeof__(*(ptr)) __v;		\
//	} *__p = (__typeof__(__p)) (ptr);	\
//	__p->__v;				\
//})

#define bt_put_unaligned(val, ptr)		0
//#define bt_put_unaligned(val, ptr)		\
//do {						\
//	struct __attribute__((packed)) {	\
//		__typeof__(*(ptr)) __v;		\
//	} *__p = (__typeof__(__p)) (ptr);	\
//	__p->__v = (val);			\
//} while(0)

 */