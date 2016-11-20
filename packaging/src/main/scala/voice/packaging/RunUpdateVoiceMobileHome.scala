package voice.packaging
//
//import java.io.File
//
//import toolbox8.jartree.client.JarTreeStandaloneClient
//import toolbox8.modules.JarTree8Modules
//import toolbox8.rpi.installer.Rpis
//import voice.modules.VoiceRpiModules
//import voice.rpi.mobile.VoiceMobilePlugger
//
///**
//  * Created by martonpapp on 16/10/16.
//  */
//object RunUpdateVoiceMobileHome {
//
//  def main(args: Array[String]): Unit = {
////    MavenTools.runMavenProject(
////      new File("../voice/rpi/home"),
////      Seq("install")
////    )
//
//    JarTreeStandaloneClient
//      .target(
//        Rpis.MobileHomeWlan.host
//      )
//      .runPlug(
//        module = VoiceRpiModules.Mobile,
//        runClassName = classOf[VoiceMobilePlugger].getName,
//        target = JarTree8Modules.Standalone
//      )
//  }
//
//}
