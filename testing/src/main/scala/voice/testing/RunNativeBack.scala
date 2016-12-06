package voice.testing

import java.io.File

import voice.jni.NativeBack

/**
  * Created by pappmar on 06/12/2016.
  */
object RunNativeBack {

  def main(args: Array[String]): Unit = {
    System.load(
      new File("c:/oracle/temp/nativeback.dll").getAbsolutePath
    )

    val nb = new NativeBack
    nb.recall("boo")

  }

}
