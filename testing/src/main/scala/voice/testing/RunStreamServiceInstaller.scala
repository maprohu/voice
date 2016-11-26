package voice.testing

import toolbox8.installer.JavaServiceTools
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis

/**
  * Created by martonpapp on 20/10/16.
  */
object RunStreamServiceInstaller {

//  val Target = Rpis.Home
//  val Target = Rpis.MobileCable
  val Target = Rpis.Central

  def main(args: Array[String]): Unit = {

    JavaServiceTools
      .upload(
        "voicer",
        JarTree8Modules.StreamApp,
        "toolbox8.jartree.streamapp.StreamAppMain",
        user = Target.serviceUser,
        bindAddress = Target.bindAddress,
        port = Target.servicePort
      )(Target)

  }

}
