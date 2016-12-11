package voice.testing

import akka.event.Logging
import org.bluez.{Adapter1, Network1}
import org.freedesktop.dbus.{DBusConnection, ObjectManager}
import voice.rpi.home.VoiceHomePlugged

/**
  * Created by maprohu on 11-12-2016.
  */
object RunDbusBluetoothClient {
  val DBusPort = 7770
  val AkkaPort = 7771

  def main(args: Array[String]): Unit = {

    DBusSniff
      .run(
        DBusPort
      )

    DBusTcp
      .run(
        rootPort = AkkaPort,
        targetHost = "localhost",
        targetPort = DBusPort,
        logLevel = Logging.DebugLevel
      )

    implicit val conn = DBusConnection.getConnection(
      s"tcp:host=localhost,port=${AkkaPort}"
    )

    try {
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

      val network =
        conn
          .getRemoteObject(
            "org.bluez",
            "/org/bluez/hci0/dev_B8_27_EB_50_3A_5D",
            classOf[Network1]
          )

      network
        .Connect("PANU")



    } finally {
      conn.disconnect()
    }

  }

}
