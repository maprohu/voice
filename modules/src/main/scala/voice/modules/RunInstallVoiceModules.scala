package voice.modules

import java.io.File

import maven.modules.builder.MavenTools


/**
  * Created by pappmar on 05/10/2016.
  */
object RunInstallVoiceModules {

  def main(args: Array[String]): Unit = {

    val root = new File("../../../../..")

    MavenTools.runMaven(
      MavenTools.pom {
        <packaging>pom</packaging>
        <modules>
          <module>{root}/maven-modules</module>
          <module>{root}/toolbox6</module>
          <module>{root}/toolbox8</module>
          <module>{root}/voice</module>
        </modules>
      },
      "install"
    )(_ => ())


  }

}
