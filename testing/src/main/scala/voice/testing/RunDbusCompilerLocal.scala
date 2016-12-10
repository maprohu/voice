package voice.testing

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, ObjectInputStream}
import java.lang.reflect.Type
import java.util

import org.freedesktop.dbus.{DBusConnection, Marshalling}
import voice.requests.compilerpi.{DBReflection, DbusCompiler}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusCompilerLocal {

  def main(args: Array[String]): Unit = {

//    val conn = DBusConnection.getConnection(
//      "tcp:host=172.24.1.1,port=7272"
//      //      "unix:abstract=/tmp/custom_dbus_name"
//    )
    val conn = DBusConnection.getConnection(
      DBusConnection.SYSTEM
    )
    val c = new DbusCompiler()
    val os = new ByteArrayOutputStream()

    c.run(null, null, os, conn)


    val obj =
      new ObjectInputStream(
        new ByteArrayInputStream(
          os.toByteArray
        )
      )
        .readObject()
        .asInstanceOf[DBReflection]

    println(obj)

    println(obj.debug.mkString("\n"))

    DBusCodeGenerator.run(
      new File("../toolbox8/rpi/dbus/src/main/scala"),
      obj
    )
  }

}



object RunDbusSandbox {
  import scala.collection.JavaConversions._
  def main(args: Array[String]): Unit = {
    val result = new util.ArrayList[Type]()
    Marshalling.getJavaType(
      "a{oa{sa{sv}}}",
      result,
      -1
    )
    println(
      result.mkString("\n")
    )
    val jt = result.get(0)
    val m = Ref.javaType(jt)
    println(m)
  }


}
