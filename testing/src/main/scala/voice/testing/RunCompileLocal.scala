package voice.testing

import java.io.{File, PipedInputStream, PipedOutputStream}

import org.zeroturnaround.zip.{FileSource, ZipEntrySource, ZipUtil}
import ammonite.ops._
import voice.requests.compilerpi.CompileRpi
import voice.requests.compilerpi.CompileRpi.Env

/**
  * Created by pappmar on 07/12/2016.
  */
object RunCompileLocal {


  def main(args: Array[String]): Unit = {
    val os = new PipedOutputStream()
    val is = new PipedInputStream(os)

    new Thread() {
      override def run(): Unit = {
        new CompileRpi().run(
          pwd / up / 'voice / 'jni / 'target / 'compile,
          is,
          System.out,
          Env(
            includes = Seq(
              "/usr/lib/jvm/java-8-oracle/include",
              "/usr/lib/jvm/java-8-oracle/include/linux"
            )
          )

        )
      }
    }.start()


    val jniRoot =
      pwd / up / 'voice / 'jni / 'src / 'main / 'c

    val entries =
      ls
        .rec(
          jniRoot
        )
        .map({ f =>
          val rel = f.relativeTo(jniRoot)
          new FileSource(rel.segments.mkString("/"), f.toIO)
        })
        .toArray[ZipEntrySource]

    ZipUtil
      .pack(
        entries,
        os
      )





  }

  def run(
    dir: File
  ) = {

  }

}
