package voice.sandbox

/**
  * Created by pappmar on 01/12/2016.
  */
object NativeStuff {


  System.loadLibrary("HelloImpl")

  def main(args: Array[String]): Unit = {
    val ns = new NativeStuff()
    ns.sayHi("world", 3)
  }

}

class NativeStuff {

  @native def sayHi(who: String, times: Int) : Unit

}
