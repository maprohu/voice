package voice.testing

import java.io.File

import org.mapdb.{DBMaker, Serializer}

/**
  * Created by maprohu on 23-11-2016.
  */
object RunMapDB {

  def main(args: Array[String]): Unit = {
    val db =
      DBMaker
        .fileDB(
          new File("../voice/target/mapdb")
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
