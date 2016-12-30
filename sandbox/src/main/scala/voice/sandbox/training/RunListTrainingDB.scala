package voice.sandbox.training

/**
  * Created by maprohu on 30-12-2016.
  */
object RunListTrainingDB {

  def main(args: Array[String]): Unit = {

    TrainingDB
      .recordingMeta
      .get()
      .data
      .filter({
        case (k, v) => v.nonEmpty
      })
      .keys
      .toSeq
      .sortBy(_.code)
      .map(_.string)
      .foreach(println)


  }

}
