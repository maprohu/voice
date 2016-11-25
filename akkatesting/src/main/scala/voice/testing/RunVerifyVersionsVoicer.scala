package voice.testing

import mvnmod.builder.PackagingTools
import toolbox8.modules.{JarTree8Modules, Toolbox8Modules}
import voice.modules.VoiceRpiModules

/**
  * Created by maprohu on 09-11-2016.
  */
object RunVerifyVersionsVoicer {

  def main(args: Array[String]): Unit = {

    PackagingTools
      .verifyVersions(
        Seq(
          JarTree8Modules.App,
          VoiceRpiModules.Home
        )
      )


  }

}
