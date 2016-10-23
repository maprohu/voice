package voice.testing

import org.freedesktop.dbus.DBusConnection
import scala.collection.JavaConversions._

/**
  * Created by maprohu on 23-10-2016.
  */
object RunDBus {

  def main(args: Array[String]): Unit = {
    val conn = DBusConnection.getConnection(DBusConnection.SESSION)

    conn
      .getNames
      .foreach(println)


    conn.disconnect()
  }

}
