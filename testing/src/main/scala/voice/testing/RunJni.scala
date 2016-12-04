package voice.testing

import java.io.File

import ammonite.ops._

/**
  * Created by pappmar on 25/11/2016.
  */
object RunJni {

  val srcdir = "../voice/jni/src/main"
  val ourdir = s"${srcdir}/java/voice/jni"
  val swgdir = s"${srcdir}/swg"
  new File(ourdir).mkdirs()
  val modulename = "libnl"
  val libtarget = "../voice/jni/target/lib"
  new File(libtarget).mkdirs()
  val targeto = s"${libtarget}/${modulename}_wrap.o"
  val targetso = s"${libtarget}/${modulename}.so"

  def main(args: Array[String]): Unit = {
    import ImplicitWd._


    println("swig")
    %(
      "swig3.0",
      "-java",
      "-package", "voice.jni",
      "-outdir", ourdir,
      s"${swgdir}/${modulename}.i"
    )

    println("gcc")
    %(
      "gcc",
      "-c", s"${swgdir}/${modulename}_wrap.c",
      "-I/usr/include/libnl3",
      "-I/usr/lib/jvm/java-8-oracle/include",
      "-I/usr/lib/jvm/java-8-oracle/include",
      "-I/usr/lib/jvm/java-8-oracle/include/linux",
      "-o", targeto
    )

    println("ld")
    %(
      "ld",
      "-G", targeto,
      "-lnl-3", "-lnl-genl-3",
      "-o", targetso
    )

  }

}
