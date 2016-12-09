package voice.requests.compilerpi

import java.io.{InputStream, ObjectOutputStream, OutputStream}

import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import toolbox8.jartree.streamapp.{Requestable, RootContext}

/**
  * Created by maprohu on 08-12-2016.
  */
class DbusCompiler extends Requestable {
//  System.setProperty("java.library.path", "/usr/lib/jni")

  override def request(
    ctx: RootContext,
    in: InputStream,
    out: OutputStream
  ): Unit = {
    System.gc()

    val dos = new ObjectOutputStream(out)

    val conn = DBusConnection.getConnection(DBusConnection.SYSTEM)

    try {
      val dbus =
        conn
          .getRemoteObject(
            "org.freedesktop.DBus",
            "/",
            classOf[DBus]
          )

      val str =
        dbus
          .ListNames()
          .mkString("\n")

      dos.writeObject(str)
      dos.flush()
    } finally {
      conn.disconnect()
    }


  }
}

object DbusCompiler {

}
