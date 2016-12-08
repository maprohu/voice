package voice.testing

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream}

import voice.requests.compilerpi.DbusCompiler

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusCompilerLocal {

  def main(args: Array[String]): Unit = {


    val c = new DbusCompiler()
    val os = new ByteArrayOutputStream()
    c.request(null, null, os)


    val obj =
      new ObjectInputStream(
        new ByteArrayInputStream(
          os.toByteArray
        )
      ).readObject()

    println(obj)


  }

}
