package voice.testing.linux

import java.io.File

import com.ochafik.lang.jnaerator.JNAerator

/**
  * Created by pappmar on 12/12/2016.
  */
object RunLinuxJNAerator {

  def main(args: Array[String]): Unit = {
    JNAerator
      .main(
        Array[String](
          "-noLibBundle",
          "-jar", "../voice/jni/target/voice-jna.jar",
          "-mode", "Maven"


        ) ++
        RunLinuxDonwloadHeaders
          .files
          .map({ f =>
            new File(RunLinuxDonwloadHeaders.TargetDir, f.tail).toString
          })
      )
  }

}
