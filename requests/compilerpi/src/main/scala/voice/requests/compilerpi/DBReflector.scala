package voice.requests.compilerpi

import com.typesafe.scalalogging.StrictLogging
import org.freedesktop.DBus
import org.freedesktop.dbus.DBusConnection
import toolbox6.logging.LogTools

import scala.xml.{Node, PrettyPrinter, XML}

/**
  * Created by pappmar on 09/12/2016.
  */
object DBReflector extends StrictLogging with LogTools {

  val Builtin = Set(
    DBInterfaceName.fromName("org.freedesktop.DBus.Introspectable"),
    DBInterfaceName.fromName("org.freedesktop.DBus.Properties")
  )

  val pp = new PrettyPrinter(1000, 2)

  def run(
    services: Seq[String],
    conn: DBusConnection
  ) : DBReflection = {
    services
      .foldLeft(DBReflection())({ (acc, service) =>
        obj(
          acc,
          service,
          "/",
          conn
        )
      })

  }

  def obj(
    acc: DBReflection,
    service: String,
    objectName: String,
    conn: DBusConnection
  ) : DBReflection = {
    logger.info(s"obj: ${service}:${objectName}")

    val introspectable =
      conn
        .getRemoteObject(
          service,
          objectName,
          classOf[DBus.Introspectable]
        )

    val introspection =
      XML
        .withSAXParser(DbusCompiler.Factory.newSAXParser())
        .loadString(
          introspectable.Introspect()
        )

    logger.info(s"introspection: ${pp.format(introspection)}")

    val ifaces =
      for {
        iface <- introspection \ "interface"
      } yield {
        iface
      }

    val ifacesAdded =
      ifaces
        .foldLeft(acc)({ (acc2, iface) =>
          val ifaceName =
            DBInterfaceName.fromName(iface \ "@name" text)

          if (
            Builtin.contains(ifaceName) ||
            acc2.interfaces.map(_.name).contains(ifaceName)
          ) {
            acc2
          } else {
            acc2.copy(
              interfaces = acc2.interfaces :+
                reflectInterface(
                  ifaceName,
                  iface
                )
            )
          }
        })


    val nodes =
      for {
        node <- introspection \ "node"
      } yield {
        node
      }

    nodes
      .foldLeft(ifacesAdded)({ (acc2, node) =>
        val nodeName = node \ "@name" text

        logger.info(s"nodeName: ${nodeName}")

        val subNodenName =
          if (objectName == "/") {
            s"/${nodeName}"
          } else {
            s"${objectName}/${nodeName}"
          }

        obj(
          acc2,
          service,
          subNodenName,
          conn
        )
      })


  }

  def reflectInterface(
    name: DBInterfaceName,
    ifaceNode: Node
  ) : DBInterface = {
    val methods =
      (ifaceNode \ "method")
        .map({ n =>
          val (o, i) =
            (n \ "arg")
              .map(n => (n, DBArg(
                name = n \ "@name" text,
                typeString = n \ "@type" text
              )))
              .partition({ case (n, a) => (n \ "@direction").text == "out" })

          require(o.size <= 1)

          DBMethod(
            name = n \ "@name" text,
            in = i.map(_._2),
            out = o.map(_._2)
          )
        })


    DBInterface(
      name = name,
      methods = methods
    )
  }

}
