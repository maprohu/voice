package voice.testing

import java.io.ObjectInputStream

import toolbox8.jartree.streamapp.{ClassLoaderConfig, Root}
import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.VoiceRequestModules
import voice.requests.common.QueryPlugged

/**
  * Created by maprohu on 29-11-2016.
  */
object RunQueryPlugged {

//  val Target = Rpis.Localhost
//  val Target = Rpis.Home.wlan
  val Target = Rpis.Mobile.homeCable
//  val Target = Rpis.Home.tunneled
//  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {

    val str : ClassLoaderConfig[Root] = StreamAppClient
      .request(
        VoiceRequestModules.Common,
        classOf[QueryPlugged].getName,
        { _ => (is, os) =>
          val dis = new ObjectInputStream(is)
          dis
            .readObject()
            .asInstanceOf[ClassLoaderConfig[Root]]
        },
        Target
      )

    println(str)

  }

}
