package voice.testing

import java.io.{File, ObjectOutputStream}

import toolbox8.leveldb.LevelDB

/**
  * Created by pappmar on 22/11/2016.
  */
object RunLevelDB2 {

  def main(args: Array[String]): Unit = {

    val dir = new File("../voice/target/leveldbtest")

    val db = LevelDB(dir)

    val table = db.longTable()

    (0 until 0x100) foreach { i =>
      val key = table.insert(s"hello${i}".getBytes)
//      new ObjectOutputStream(System.out).writeObject(key)
      println(key)
    }

    db.cancelable.cancel()

  }

}
