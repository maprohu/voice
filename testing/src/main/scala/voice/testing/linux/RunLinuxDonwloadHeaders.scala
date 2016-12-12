package voice.testing.linux

import java.io._

import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.BoundedInputStream
import toolbox8.jartree.testing.StreamAppClient
import voice.environment.Rpis
import voice.modules.VoiceRequestModules
import voice.requests.common.{DumpFiles, DumpFilesInput, DumpLog}

/**
  * Created by pappmar on 12/12/2016.
  */
object RunLinuxDonwloadHeaders {

  val Target = Rpis.Home.tunneled

  val TargetDir = new File("../voice/jni/src/main/c")

  val files = Seq(
    "/usr/include/bluetooth/hci.h"
  )


  def main(args: Array[String]): Unit = {

    StreamAppClient
      .request(
        VoiceRequestModules.Common,
        classOf[DumpFiles].getName,
        { _ =>

          { (in, out) =>

            val dos = new ObjectOutputStream(out)
            dos.writeObject(
              DumpFilesInput(
                files
              )
            )


            files
              .foreach({ f =>
                val dis = new DataInputStream(in)

                println(f)
                val l = dis.readLong()
                val tf = new File(TargetDir, f.tail)
                tf.getParentFile.mkdirs()
                val fos = new FileOutputStream(tf)

                try {
                  IOUtils.copyLarge(
                    new BoundedInputStream(dis, l),
                    fos
                  )
                } finally {
                  fos.close()
                }

              })

          }
        },
        Target
      )

  }

}
