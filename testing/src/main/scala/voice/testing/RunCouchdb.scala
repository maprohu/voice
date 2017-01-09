package voice.testing

import com.ibm.couchdb._
/**
  * Created by pappmar on 09/01/2017.
  */
object RunCouchdb {

  def main(args: Array[String]): Unit = {

    val couch = CouchDb("127.0.0.1", 5984)

    couch
      .dbs
      .getAll
      .get
      .unsafePerformSync
      .foreach(println)

  }

}
