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
          "-I", s"${targetDir}${libs.IncludeDir}",
          "-I", s"${targetDir}${libs.IncludeDir}/linux",
          "-I", s"${targetDir}${libs.LibIncludeDir}",
          "-o", "../voice/linux/jnalib",
          "-mode", "Maven",
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