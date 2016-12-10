package voice.testing

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, ObjectInputStream}
import java.lang.reflect.Type
import java.util

import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{DBusConnection, Marshalling, ObjectManager}
import voice.requests.compilerpi.{DBReflection, DbusCompiler}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusTestLocal {

  def main(args: Array[String]): Unit = {
    implicit val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)

    val om =
      ObjectManager
        .Instances
        .`org.bluez`
        .`/`
        .getRemoteObject

    import scala.collection.JavaConversions._
    println(
      om
        .GetManagedObjects()
        .toSeq
        .mkString("\n")

    )

    val nm =
      NetworkManager
        .Instances
        .`org.freedesktop.NetworkManager`
        .`/org/freedesktop/NetworkManager`
        .getRemoteObject

    println(
      nm.GetPermissions()
    )
    val l = nm.GetLogging()
    println(
      l.t1
    )
    println(
      l.t2
    )

    import java.lang.reflect.{Proxy => JProxy}

    val ih = JProxy.getInvocationHandler(nm)

    println(
      NetworkManager
        .Props
        .Version
        .read(nm)
    )

    conn.disconnect()
  }

}



