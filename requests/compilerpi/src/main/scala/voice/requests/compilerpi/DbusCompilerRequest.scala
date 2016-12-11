package voice.requests.compilerpi

import java.io.{InputStream, ObjectOutputStream, OutputStream}

import org.freedesktop.DBus
import org.freedesktop.dbus.{DBusConnection, Position, Tuple}
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import toolbox8.rpi.dbus.DBF

import scala.xml.{PrettyPrinter, XML}

/**
  * Created by maprohu on 08-12-2016.
  */
class DbusCompilerRequest extends Requestable {

  override def request(
    ctx: RootContext,
    in: InputStream,
    out: OutputStream
  ): Unit = {
  }

}

