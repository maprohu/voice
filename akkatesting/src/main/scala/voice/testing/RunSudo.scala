package voice.testing

/**
  * Created by maprohu on 23-11-2016.
  */
object RunSudo {

  def main(args: Array[String]): Unit = {
    Runtime.getRuntime.exec("touch ../voice/target/csuf")
  }

}
