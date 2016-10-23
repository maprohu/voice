package voice.testing

import org.freedesktop.DBus
import org.freedesktop.DBus.Introspectable
import org.freedesktop.dbus.DBusConnection

import scala.collection.JavaConversions._

/**
  * Created by maprohu on 23-10-2016.
  */
object RunDBus {

  def main(args: Array[String]): Unit = {
    val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)

    conn
      .getNames
      .foreach(println)

    println(
      conn
        .getUniqueName
    )

    val dbus = conn
      .getRemoteObject(
        "org.freedesktop.DBus",
        "/",
        classOf[DBus]
      )

    dbus
      .ListNames()
      .foreach(println)

    val bluez = conn
      .getRemoteObject(
        "org.bluez",
        "/",
        classOf[Introspectable]
      )


    conn.disconnect()
  }

}
