package voice.sandbox.training

import voice.storage.Vowels

/**
  * Created by maprohu on 30-12-2016.
  */
object RunTrainingVowelDisplay {

  def main(args: Array[String]): Unit = {
    import voice.storage.Vowels._
    show(E)
  }

  def show(s: Vowels.Value) = {

    val datas =
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

    Display.showData(datas)
  }

}
