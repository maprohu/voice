package voice.packaging

//import java.io.File
//
//import toolbox8.jartree.client.JarTreeStandaloneClient
//import toolbox8.jartree.extra.server.ExecPlugger
//import toolbox8.modules.{Extra8Modules, JarTree8Modules}
//import toolbox8.rpi.installer.Rpis
//import voice.modules.{VoiceModules, VoiceRpiModules}
//import voice.rpi.home.VoiceHomePlugger
//
///**
//  * Created by martonpapp on 16/10/16.
//  */
//object RunUpdateVoiceHome {
//
////  val Target = Rpis.Localhost
//  val Target = Rpis.Home
//
//  val module = Extra8Modules.Server
//  val runClassName = classOf[ExecPlugger].getName
////  val module = VoiceRpiModules.Home
////  val runClassName = classOf[VoiceHomePlugger].getName
//
//  def main(args: Array[String]): Unit = {
////    MavenTools.runMavenProject(
////      new File("../voice/rpi/home"),
////      Seq("install")
////    )
//
//
//    JarTreeStandaloneClient
//      .target(
//        Target.host,
//        Target.servicePort
//      )
//      .runPlug(
//        module = module,
//        runClassName = runClassName,
//        target = JarTree8Modules.Standalone
//      )
//  }
//
//}
