package voice.testing

import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory}

import org.freedesktop.dbus.bin.CreateInterface

/**
  * Created by maprohu on 27-10-2016.
  */
object RunDBus3 {

  def main(args: Array[String]): Unit = {
    System.setProperty(
      classOf[DocumentBuilderFactory].getName,
      classOf[DBF].getName
    )

    CreateInterface
      .main(
        Array(
          "--system",
          "--no-ignore-builtin",
          "org.bluez",
          "/"
        )
      )

  }


}

class DBF extends com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl { factory =>

  // disable DTD validation
  factory.setValidating(false)
  factory.setFeature("http://xml.org/sax/features/validation", false)
  factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false)
  factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
  factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
  factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)

}
