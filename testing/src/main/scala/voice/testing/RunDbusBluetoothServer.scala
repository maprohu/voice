package voice.testing

import java.util.UUID

import com.typesafe.scalalogging.StrictLogging
import cx.ath.matthew.utils.Hexdump
import org.bluez.NetworkServer1
import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{DBusConnection, ObjectManager, Variant}
import org.freedesktop.networkmanager.Settings
import toolbox6.logging.LogTools
import voice.rpi.home.VoiceHomePlugged

import scala.io.StdIn

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusBluetoothServer extends StrictLogging with LogTools {

  val BridgeName = "brpan0"

  def main(args: Array[String]): Unit = {
    implicit val conn = DBusConnection.getConnection(
      s"tcp:host=localhost,port=${RunDbusTcpRoot.AkkaPort}"
//      "tcp:host=172.24.1.1,port=7272"
//      "unix:abstract=/tmp/custom_dbus_name"
    )

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


    val settings =
      Settings
        .Instances
        .`org.freedesktop.NetworkManager`
        .`/org/freedesktop/NetworkManager/Settings`
        .getRemoteObject

    import scala.collection.JavaConversions._
    val uuid = UUID.randomUUID().toString
    println(uuid)
    quietly {
      settings
        .AddConnectionUnsaved(
          Map[String, java.util.Map[String, Variant[_]]](
            "connection" -> Map(
              "id" -> new Variant(s"bridge-${BridgeName}"),
              "uuid" -> new Variant(uuid),
              "type" -> new Variant("bridge")
            ),
            "bridge" -> Map(
              "interface-name" -> new Variant(BridgeName)
            )
          )
        )
    }
    quietly {
      val networkServer =
        NetworkServer1
          .Instances
          .`org.bluez`
          .`/org/bluez/hci0`
          .getRemoteObject

      networkServer
        .Register(
          "NAP",
          BridgeName
        )
    }

    StdIn.readLine("enter...")

    conn.disconnect()
  }

}



//class SniffClassLoader extends ClassLoader {
//  override def loadClass(name: String): Class[_] = {
//    println(name)
//    getParent.loadClass(name)
//  }
//}
//
//
//
object RunSniffDbus {
  val SocatPort = VoiceHomePlugged.DBusPort
  val AbstractName = "/tmp/socat_dbus"

  def main(args: Array[String]): Unit = {
    DBusSniff.run(
      SocatPort
    )
    StdIn.readLine("enter...")
  }

}

object DBusSniff {

  def stopOnExit(p: Process) = {
    Runtime.getRuntime.addShutdownHook(
      new Thread() {
        override def run(): Unit = {
          println(s"stopping process: ${p}")
          p.destroy()
        }
      }
    )
  }

  def stupidlyEncode(data: String): String = {
    Hexdump.toHex(data.getBytes).replaceAll(" ", "")
  }


  def run(
    bindPort: Int
  ): Unit = {

    println(s"launching socat on port ${bindPort} ")
    stopOnExit {
      new ProcessBuilder()
        .command(
          "sudo",
          "socat",
          s"TCP-LISTEN:${bindPort},reuseaddr,fork",
          "UNIX-CONNECT:/var/run/dbus/system_bus_socket"
        )
        .start()
    }

  }

//    println("starting socat reverse")
//    stopOnExit {
//      new ProcessBuilder()
//        .command(
////          "sudo",
//          "socat",
//          s"ABSTRACT-LISTEN:${AbstractName},fork",
//          s"TCP:localhost:${AkkaPort}"
//        )
//        .start()
//    }


}