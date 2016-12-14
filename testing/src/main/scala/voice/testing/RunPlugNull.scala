package voice.testing

import toolbox8.jartree.pluggednull.{NullPlugged, NullPluggedRoot}
import toolbox8.jartree.testing.StreamAppClient
import toolbox8.modules.{JarTree8Modules, Toolbox8Modules}
import voice.environment.Rpis

/**
  * Created by pappmar on 06/12/2016.
  */
object RunPlugNull {

//  val Target = Rpis.Localhost
  val Target = Rpis.Mobile.homeCable
//  val Target = Rpis.Central.tunneled

  def main(args: Array[String]): Unit = {

    StreamAppClient
      .plug(
        JarTree8Modules.PluggedNull,
        classOf[NullPluggedRoot].getName,
        Target
      )

  }

}
