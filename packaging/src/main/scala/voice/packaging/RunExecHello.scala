package voice.packaging

import akka.stream.scaladsl.{Flow, Source, StreamConverters}
import maven.modules.builder.ModulePath
import toolbox8.akka.stream.Sinks
import toolbox8.jartree.client.JarTreeStandaloneClient
import toolbox8.jartree.extra.hello.HelloExec
import toolbox8.modules.{Extra8Modules, JarTree8Modules}
import toolbox8.rpi.installer.Rpis
import voice.modules.VoiceRpiModules
import voice.rpi.home.VoiceHomePlugger

/**
  * Created by martonpapp on 16/10/16.
  */
object RunExecHello {

  val Target = Rpis.Localhost
  val module = Extra8Modules.Hello
  val runClassName = classOf[HelloExec].getName

  def main(args: Array[String]): Unit = {
//    MavenTools.runMavenProject(
//      new File("../voice/rpi/home"),
//      Seq("install")
//    )


    JarTreeStandaloneClient
      .target(
        Target.host,
        Target.port
      )
      .runExec(
        module = module,
        runClassName = runClassName,
        target = ModulePath(Extra8Modules.Shared, None),
        runWith =
          Flow
            .fromSinkAndSource(
              Sinks.Dump,
              Source.maybe
            )
      )
  }

}
