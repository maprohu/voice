package voice.testing

import java.io.File

import mvnmod.builder.MavenTools
import toolbox8.modules.RpiModules
import voice.environment.Rpis

/**
  * Created by martonpapp on 19/10/16.
  */
object RunRemote {

  implicit val Target = Rpis.Home

  def main(args: Array[String]): Unit = {

    val Name = "remote"
    MavenTools
      .runMaven(
        MavenTools.pom(
          <build>
            <finalName>{Name}</finalName>
            <plugins>
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                  <archive>
                    <manifest>
                      <addClasspath>true</addClasspath>
                      <mainClass>toolbox8.rpi.remote.RemoteMain</mainClass>
                    </manifest>
                  </archive>
                  <outputDirectory>target/lib</outputDirectory>
                </configuration>
              </plugin>
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.10</version>
                <executions>
                  <execution>
                    <id>copy-dependencies</id>
                    <phase>package</phase>
                    <goals>
                      <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                      <outputDirectory>target/lib</outputDirectory>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
              </plugins>
            </build>
          <dependencies>
            {
            RpiModules.Remote.pomDependency
            }
          </dependencies>
        ),
        "package"
      ) { dir =>
        import ammonite.ops._
        import toolbox6.ssh.SshTools._
        implicit val session = connect

        val TargetDir = "/tmp/tbrpiremote"
        command(s"mkdir -p ${TargetDir}")
        ls(Path(dir.getAbsoluteFile) / 'target / 'lib)
          .foreach({ jar =>
            scp(
              new File(jar.toString()),
              s"${TargetDir}/${jar.name}"
            )
          })
        command(s"/usr/bin/java -jar ${TargetDir}/${Name}.jar")
        command("which java")

        session.disconnect()

      }

  }

}
