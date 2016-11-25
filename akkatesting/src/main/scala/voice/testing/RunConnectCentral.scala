package voice.testing

import voice.common.SshConnectionDetails

/**
  * Created by pappmar on 25/11/2016.
  */
object RunConnectCentral {

  def main(args: Array[String]): Unit = {
    println(
      SshConnectionDetails.Central
    )
  }

}
