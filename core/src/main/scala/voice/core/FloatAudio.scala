package voice.core

/**
  * Created by pappmar on 23/11/2016.
  */
object FloatAudio {

  trait Source {
    def length: Long
    def get(buffer: Array[Float]) : Unit
  }

}
