package voice.linux.extra

import com.sun.jna.Native
import voice.linux.jna.c.CLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
object NativeTools {

  def ensureSuccess(what: => Int) : Int = {
    val res = what
    if (res < 0) {
      throw new Exception(
        s"failure: ${
          synchronized { CLibrary.INSTANCE.strerror(Native.getLastError).getString(0) }
        }"
      )
    }
    res
  }

}
