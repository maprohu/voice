package voice.testing

import java.io.File

import org.mapdb.{DBMaker, Serializer}

/**
  * Created by maprohu on 23-11-2016.
  */
object RunMapDB {

  def main(args: Array[String]): Unit = {
    val dir =
      new File("../voice/target/mapdb/test")
    dir.getParentFile.mkdirs()
    val db =
      DBMaker
        .fileDB(
          dir
        )
        .fileMmapEnableIfSupported()
        .make()

    val map =
      db
        .hashMap("boo", Serializer.STRING, Serializer.BYTE_ARRAY)
        .createOrOpen()

    map.put(
      "alfa",
      Array[Byte](3,3,3,3)
    )

    db.close()



  }

}
