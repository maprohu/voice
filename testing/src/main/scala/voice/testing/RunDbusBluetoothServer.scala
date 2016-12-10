package voice.testing

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.{ActorMaterializer, Attributes}
import akka.stream.scaladsl.{Sink, Tcp}
import org.freedesktop.NetworkManager
import org.freedesktop.dbus.{RootDBusConnection, ObjectManager}
import voice.environment.Rpis

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn

/**
  * Created by maprohu on 08-12-2016.
  */
object RunDbusBluetoothServer {

  def main(args: Array[String]): Unit = {
    val conn = RootDBusConnection.getConnection(
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
    val cl = new SniffClassLoader()
    val c = cl.loadClass("voice.testing.SniffDbus")
    val ci = c.newInstance()
    val m = c.getMethod("run")
    m.invoke(ci)
  }
}

class SniffDbus {
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
                .map({ bs =>
                  println(s"in: ${bs.utf8String} - ${bs}")
                  bs
                })
                .join(
                  c
                    .flow
                    .map({ bs =>
                      println(s"out: ${bs.utf8String} - ${bs}")
                      bs
                    })
                )
                .run()
            })
          )
          .run(),
        Duration.Inf
      )
    )

    println("starting socat reverse")
    stopOnExit {
      new ProcessBuilder()
        .command(
//          "sudo",
          "socat",
          s"ABSTRACT-LISTEN:${AbstractName},fork",
          s"TCP:localhost:${AkkaPort}"
        )
        .start()
    }

    val addr =
//      s"tcp:host=localhost,port=${AkkaPort},guid=0"
          s"unix:abstract=${AbstractName}"

    println(addr)
    val conn = RootDBusConnection.getConnection(
      addr
    )

    StdIn.readLine("enter...")

  }
}