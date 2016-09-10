package voice.sandbox

import java.io.FileInputStream

import edu.cmu.sphinx.api.{Configuration, StreamSpeechRecognizer}

/**
  * Created by martonpapp on 10/09/16.
  */
object RunSphinx4 {

  def main(args: Array[String]): Unit = {
    val configuration = new Configuration
    configuration
      .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us")
    configuration
      .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict")
    configuration
      .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")

    val recognizer = new StreamSpeechRecognizer(configuration)
    val stream = new FileInputStream("../voice/sandbox/data/what_is_your_name2.wav")
    recognizer.startRecognition(stream)

    Iterator
      .continually(recognizer.getResult)
      .takeWhile(_ != null)
      .foreach({ result =>
        println(result.getHypothesis)
      })

    recognizer.stopRecognition()
  }

}
