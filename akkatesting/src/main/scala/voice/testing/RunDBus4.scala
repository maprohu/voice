package voice.testing

import java.io.File

//import toolbox8.rpi.dbus.DBusTools
//
///**
//  * Created by maprohu on 23-10-2016.
//  */
//object RunDBus4 {
//
//  def main(args: Array[String]): Unit = {
//    val conn = DBusTools.connect
//
//
//    conn
//      .bluez
//      .untyped("/org/bluez")
//      .asIntrospectable
//      .generateJava(new File("../toolbox8/rpi/bluetooth/src/main/java"))
//
//    conn
//      .bluez
//      .untyped("/org/bluez/hci0")
//      .asIntrospectable
//      .generateJava(new File("../toolbox8/rpi/bluetooth/src/main/java"))
//
//    conn
//      .bluez
//      .untyped("/org/bluez/hci0/dev_FF_FF_20_00_1E_FA")
//      .asIntrospectable
//      .generateJava(new File("../toolbox8/rpi/bluetooth/src/main/java"))
//
//    conn
//      .bluez
//      .untyped("/org/bluez/hci0/dev_0C_E0_E4_F2_34_C0")
//      .asIntrospectable
//      .generateJava(new File("../toolbox8/rpi/bluetooth/src/main/java"))
//
//
//    conn.close
//
//
//
//  }
//
//}
