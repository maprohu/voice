package voice.core

import ammonite.ops._
/**
  * Created by maprohu on 19-11-2016.
  */
object RunRenameNato {

  def main(args: Array[String]): Unit = {
    val natoDir = pwd / up / 'voice / 'core / 'src / 'main / 'resources / 'nato

    ls(natoDir)
      .foreach { f =>
        mv(f,  f / up / f.segments.last.reverse.take(5).reverse)
      }



  }

}
