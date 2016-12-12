package voice.linux.generator

import java.io.File

import com.ochafik.lang.jnaerator.JNAerator
import sbt.io.IO

/**
  * Created by pappmar on 12/12/2016.
  */
object RunLinuxJNAerator {

  val TargetMavenDir = "../voice/linux/jnalib"

  def main(args: Array[String]): Unit = {
    val tf = new File(TargetMavenDir)

    IO.delete(tf)
    tf.mkdirs()

    JNAerator
      .main(
        Array[String](
          "-noLibBundle",
          "-mavenGroupId", "voice",
          "-mavenArtifactId", "voice-linux-jnalib",
          "-mavenVersion", "2-SNAPSHOT",
          "-D_GNU_SOURCE=1",
          "-I", s"${RunLinuxDonwloadHeaders.TargetDirPath}/usr/include",
          "-I", s"${RunLinuxDonwloadHeaders.TargetDirPath}/usr/include/linux",
          "-I", s"${RunLinuxDonwloadHeaders.TargetDirPath}/usr/include/arm-linux-gnueabihf",
          "-o", "../voice/linux/jnalib",
          "-mode", "Maven",
          "-rootPackage", "voice.linux.jna"
        ) ++
        LinuxLibraries
          .libs
          .flatMap({ lib =>
            Seq(
              "-library", lib.name
            ) ++
              lib
                .headers
                .map({ f =>
                  new File(new File(RunLinuxDonwloadHeaders.TargetDir.getPath), f.tail).toString
                })
          })
      )
  }

}
