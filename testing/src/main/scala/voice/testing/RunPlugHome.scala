package voice.testing

import mvnmod.builder.{MavenTools, Module}
import toolbox6.jartree.packaging.ClassLoaderPackager
import toolbox8.jartree.testing.StreamAppClient
import voice.central.CentralRoot
import voice.common.SshConnectionDetails
import voice.environment.Rpis
import voice.modules.{VoiceModules, VoiceRpiModules}
import voice.rpi.home.VoiceHomeRoot

/**
  * Created by maprohu on 26-11-2016.
  */
object RunPlugHome {

  val Target = Rpis.Home.wlan
//  val Target = Rpis.Localhost

  def main(args: Array[String]): Unit = {
    import ammonite.ops._

    val meta = ClassLoaderPackager
      .createResource(
        VoiceRpiModules.Home,
        { res =>
          val p = Path(res.getCanonicalFile.getAbsolutePath)
          val jsonPath = SshConnectionDetails.localPath(
            Rpis.Central.name.toLowerCase
          )
          cp(
            jsonPath,
            p / SshConnectionDetails.jsonName(Rpis.Central.name.toLowerCase)
          )
        }
      )

    MavenTools.runMavens(
      meta,
      Seq("install")
    )(dir =>
      ()
    )


    StreamAppClient
      .plugMulti(
        Seq(
          VoiceRpiModules.Home,
          Module.hasMavenCoordinates2Module(
            meta.coordinates
          )
        ),
        classOf[VoiceHomeRoot].getName,
        Target
      )
  }

}
