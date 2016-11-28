package voice.testing

import toolbox8.installer.JavaServiceTools
import toolbox8.modules.JarTree8Modules

/**
  * Created by maprohu on 28-11-2016.
  */
object RunDownloaderPom {

  def main(args: Array[String]): Unit = {
    val pom =
      JavaServiceTools
        .downloaderPom(
          JarTree8Modules.StreamApp,
          "/opt/voicer/lib"
        )

    println(pom)
  }

}
