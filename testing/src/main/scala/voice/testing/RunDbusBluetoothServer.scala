package voice.testing

import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{DBusConnection, ObjectManager}

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusBluetoothServer {

  def main(args: Array[String]): Unit = {
    val conn = DBusConnection.getConnection(
//      "tcp:host=172.24.1.1,port=7272"
      "unix:abstract=/tmp/custom_dbus_name"
    )

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

    val nm =
      conn.getRemoteObject(
        "org.freedesktop.NetworkManager",
        "/org/freedesktop/NetworkManager",
        classOf[NetworkManager]
      )

    println(
      nm.GetPermissions()
    )

    conn.disconnect()
  }

}



