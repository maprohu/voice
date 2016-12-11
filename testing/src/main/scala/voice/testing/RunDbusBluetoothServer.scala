package voice.testing

import java.util.UUID

import com.typesafe.scalalogging.StrictLogging
import org.bluez.NetworkServer1
import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{DBusConnection, ObjectManager, Variant}
import org.freedesktop.networkmanager.Settings
import toolbox6.logging.LogTools

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
          "GN",
          BridgeName
        )
    }

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
//object RunSniffDbus {
//  def main(args: Array[String]): Unit = {
//    run()
//  }
//
//  def stopOnExit(p: Process) = {
//    Runtime.getRuntime.addShutdownHook(
//      new Thread() {
//        override def run(): Unit = {
//          println(s"stopping process: ${p}")
//          p.destroy()
//        }
//      }
//    )
//  }
//
//  def stupidlyEncode(data: String): String = {
//    Hexdump.toHex(data.getBytes).replaceAll(" ", "")
//  }
//
//  val SocatPort = 7272
//  val AkkaPort = 7273
//  val AbstractName = "/tmp/socat_dbus"
//
//  def run(): Unit = {
//
//    println(s"launching socat on port ${SocatPort} ")
//    stopOnExit {
//      new ProcessBuilder()
//        .command(
//          "sudo",
//          "socat",
//          s"TCP-LISTEN:${SocatPort},reuseaddr,fork",
//          "UNIX-CONNECT:/var/run/dbus/system_bus_socket"
//        )
//        .start()
//    }
//
//    implicit val actorSystem = ActorSystem()
//    implicit val materializer = ActorMaterializer()
//    import actorSystem.dispatcher
//
//    println(s"runnig akka at ${AkkaPort}")
//    println(
//      Await.ready(
//        Tcp()
//          .bind(
//            "127.0.0.1",
//            AkkaPort
//          )
//          .to(
//            Sink.foreach({ c =>
//              Tcp()
//                .outgoingConnection(
//                  "localhost",
//                  SocatPort
//                )
////                .map({ bs =>
////                  println(s"in: ${bs.utf8String} - ${bs}")
////                  bs
////                })
//                .join(
//                  c
//                    .flow
////                    .map({ bs =>
////                      println(s"orig: ${bs.utf8String} - ${bs}")
////                      bs
////                    })
//                    .prefixAndTail(1)
//                    .flatMapConcat({
//                      case (Seq(head), tail) =>
//                        Source
//                          .single(
//                            head
//                          )
//                          .concat(
//                            tail
//                              .prefixAndTail(1)
//                              .flatMapConcat({
//                                case (Seq(head2), tail2) =>
//                                  Source
//                                    .single(
//                                      ByteString(s"AUTH EXTERNAL ${stupidlyEncode("0")}\r\n")
//                                    )
//                                    .concat(tail2)
//                              })
//                          )
//                    })
////                    .map({ bs =>
////                      println(s"trf: ${bs.utf8String} - ${bs}")
////                      bs
////                    })
//                )
//                .run()
//                .onComplete(println)
//            })
//          )
//          .run(),
//        Duration.Inf
//      )
//    )
//
////    println("starting socat reverse")
////    stopOnExit {
////      new ProcessBuilder()
////        .command(
//////          "sudo",
////          "socat",
////          s"ABSTRACT-LISTEN:${AbstractName},fork",
////          s"TCP:localhost:${AkkaPort}"
////        )
////        .start()
////    }
//
//    val addr =
//      s"tcp:host=localhost,port=${AkkaPort}"
////          s"unix:abstract=${AbstractName}"
//
//    println(addr)
//    val conn = DBusConnection.getConnection(
//      addr
//    )
//
//    val nm =
//      conn.getRemoteObject(
//        "org.freedesktop.NetworkManager",
//        "/org/freedesktop/NetworkManager",
//        classOf[NetworkManager]
//      )
//
//    println(
//      nm.GetPermissions()
//    )
//
//    val settings =
//      conn.getRemoteObject(
//        "org.freedesktop.NetworkManager",
//        "/org/freedesktop/NetworkManager/Settings",
//        classOf[Settings]
//      )
//
//
//    import scala.collection.JavaConversions._
//    settings
//      .AddConnectionUnsaved(
//        Map[String, java.util.Map[String, Variant[_]]](
//          "connection" -> Map(
//            "id" -> new Variant("br2")
//          )
//
//        )
//
//      )
//
//
//    StdIn.readLine("enter...")
//
//  }
//}