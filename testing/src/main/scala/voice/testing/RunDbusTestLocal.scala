package voice.testing

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, ObjectInputStream}
import java.lang.reflect.Type
import java.util

import org.freedesktop.dbus.{DBusConnection, Marshalling, ObjectManager}
import voice.requests.compilerpi.{DBReflection, DbusCompiler}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusTestLocal {

  def main(args: Array[String]): Unit = {
    val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)

    val om =
      conn.getRemoteObject(
        "org.bluez",
        "/",
        classOf[ObjectManager]
      )

    import scala.collection.JavaConversions._
    println(
      om
        .GetManagedObjects()
        .toSeq
        .mkString("\n")

    )

    conn.disconnect()
  }

}



