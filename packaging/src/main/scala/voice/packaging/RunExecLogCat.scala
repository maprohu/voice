package voice.packaging

//import mvnmod.builder.ModulePath
//import toolbox8.akka.stream.Flows
//import toolbox8.jartree.client.JarTreeStandaloneClient
//import toolbox8.jartree.extra.hello.HelloExec
//import toolbox8.modules.Extra8Modules
//import toolbox8.rpi.installer.Rpis
//import voice.modules.VoiceRpiModules
//import voice.rpi.exec.LogCat
//
///**
//  * Created by martonpapp on 16/10/16.
//  */
//object RunExecLogCat {
//
//  val Target = Rpis.Localhost
//  val module = VoiceRpiModules.Exec
//  val runClassName = classOf[LogCat].getName
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
//        Target.sshPort
//      )
//      .runExec(
//        module = module,
//        runClassName = runClassName,
//        target = ModulePath(Extra8Modules.Shared, None),
//        runWith = Flows.Dump
//      )
//  }
//
//}
