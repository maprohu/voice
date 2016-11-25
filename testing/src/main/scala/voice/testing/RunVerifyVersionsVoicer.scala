package voice.testing

import mvnmod.builder.PackagingTools
import toolbox8.modules.JarTree8Modules
import voice.modules.{VoiceModules, VoiceRpiModules}

object RunVerifyVersionsVoicer {

  def main(args: Array[String]): Unit = {

    PackagingTools
      .verifyVersions(
        Seq(
          JarTree8Modules.StreamApp,
          VoiceRpiModules.Exec,
          VoiceModules.Testing
        )
      )


  }

}
