package voice.testing

import toolbox8.installer.JavaServiceTools
import toolbox8.modules.JarTree8Modules
import voice.environment.Rpis

/**
  * Created by martonpapp on 20/10/16.
  */
object RunStreamServiceInstaller {

//  val Target = Rpis.Home.remote("85.247.194.46")
//  val Target = Rpis.Home.wlan
  val Target = Rpis.Mobile.homeCable
//  val Target = Rpis.Home.direct
//  val Target = Rpis.MobileCable
//  val Target = Rpis.Central.remote

  def main(args: Array[String]): Unit = {

    JavaServiceTools
      .uploadWithPom(
        "voicer",
        JarTree8Modules.StreamApp,
        "toolbox8.jartree.streamapp.StreamAppMain",
        processUser = Target.processUser,
        bindAddress = Target.bindAddress,
        port = Target.servicePort
      )(Target)

  }

}
