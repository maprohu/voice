package voice.testing

import org.freedesktop.DBus
import org.freedesktop.DBus.Introspectable
import org.freedesktop.dbus.DBusConnection
import toolbox6.macros.Macros._
import toolbox8.rpi.dbus.DBusTools

/**
  * Created by maprohu on 23-10-2016.
  */
object RunDBus2 {

  def main(args: Array[String]): Unit = {
    val conn = DBusTools.connect

    conn
      .dbus
      .instance
      .ListNames()
      .foreach(println)

    println(
      conn
        .bluez
        .root[Introspectable]
        .introspect()
    )

//    println(
//      conn
//        .bluez
//        .get[Introspectable]("/org")
//        .introspect()
//    )


    conn.close



  }

}
