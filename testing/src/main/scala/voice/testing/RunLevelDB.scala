package voice.testing

import java.io.File

import toolbox8.jartree.extra.hello.HelloLeveldbExec

/**
  * Created by maprohu on 02-11-2016.
  */
object RunLevelDB {

  def main(args: Array[String]): Unit = {
    val dbDir = new File("../voice/target/leveldb")
    println(
      HelloLeveldbExec.run(
        dbDir
      )
    )
  }


}
