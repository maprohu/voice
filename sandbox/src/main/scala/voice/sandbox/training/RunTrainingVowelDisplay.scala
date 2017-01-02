package voice.sandbox.training

import voice.storage.Vowels

/**
  * Created by maprohu on 30-12-2016.
  */
object RunTrainingVowelDisplay {

  def main(args: Array[String]): Unit = {
    import voice.storage.Vowels._
    show(A)
  }

  def data(s: Vowels.Value) = {
    TrainingDB
      .vowelRecordingMeta
      .get()
      .data
      .get(s)
      .toSeq
      .map({ ids =>
        ids
          .map({ id =>
            TrainingDB
              .vowelRecordingData
              .get(id)
          })
      })
  }

  def show(s: Vowels.Value) = {
    val datas = data(s)

    Display.showData(datas)
  }

}
