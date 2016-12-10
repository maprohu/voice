package voice.testing

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.{ActorMaterializer, Attributes}
import akka.stream.scaladsl.{Sink, Source, Tcp}
import akka.util.ByteString
import cx.ath.matthew.utils.Hexdump
import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{DBusConnection, ObjectManager, Variant}
import org.freedesktop.networkmanager.Settings
import voice.environment.Rpis

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

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



class SniffClassLoader extends ClassLoader {
  override def loadClass(name: String): Class[_] = {
    println(name)
    getParent.loadClass(name)
  }
}



object RunSniffDbus {
  def main(args: Array[String]): Unit = {
    run()
  }

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

  val SocatPort = 7272
  val AkkaPort = 7273
  val AbstractName = "/tmp/socat_dbus"

  def run(): Unit = {

    println(s"launching socat on port ${SocatPort} ")
    stopOnExit {
      new ProcessBuilder()
        .command(
          "sudo",
          "socat",
          s"TCP-LISTEN:${SocatPort},reuseaddr,fork",
          "UNIX-CONNECT:/var/run/dbus/system_bus_socket"
        )
        .start()
    }

    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import actorSystem.dispatcher

    println(s"runnig akka at ${AkkaPort}")
    println(
      Await.ready(
        Tcp()
          .bind(
            "127.0.0.1",
            AkkaPort
          )
          .to(
            Sink.foreach({ c =>
              Tcp()
                .outgoingConnection(
                  "localhost",
                  SocatPort
                )
//                .map({ bs =>
//                  println(s"in: ${bs.utf8String} - ${bs}")
//                  bs
//                })
                .join(
                  c
                    .flow
//                    .map({ bs =>
//                      println(s"orig: ${bs.utf8String} - ${bs}")
//                      bs
//                    })
                    .prefixAndTail(1)
                    .flatMapConcat({
                      case (Seq(head), tail) =>
                        Source
                          .single(
                            head
                          )
                          .concat(
                            tail
                              .prefixAndTail(1)
                              .flatMapConcat({
                                case (Seq(head2), tail2) =>
                                  Source
                                    .single(
                                      ByteString(s"AUTH EXTERNAL ${stupidlyEncode("0")}\r\n")
                                    )
                                    .concat(tail2)
                              })
                          )
                    })
//                    .map({ bs =>
//                      println(s"trf: ${bs.utf8String} - ${bs}")
//                      bs
//                    })
                )
                .run()
                .onComplete(println)
            })
          )
          .run(),
        Duration.Inf
      )
    )

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

    val addr =
      s"tcp:host=localhost,port=${AkkaPort}"
//          s"unix:abstract=${AbstractName}"

    println(addr)
    val conn = DBusConnection.getConnection(
      addr
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

    val settings =
      conn.getRemoteObject(
        "org.freedesktop.NetworkManager",
        "/org/freedesktop/NetworkManager/Settings",
        classOf[Settings]
      )


    import scala.collection.JavaConversions._
    settings
      .AddConnectionUnsaved(
        Map[String, java.util.Map[String, Variant[_]]](
          "connection" -> Map(
            "id" -> new Variant("br2")
          )

        )

      )


    StdIn.readLine("enter...")

  }
}