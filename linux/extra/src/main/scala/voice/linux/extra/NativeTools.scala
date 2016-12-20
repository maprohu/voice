package voice.linux.extra

import com.ochafik.lang.jnaerator.runtime.NativeSize
import com.sun.jna.{Memory, Native}
import voice.linux.jna.c.CLibrary

/**
  * Created by pappmar on 20/12/2016.
  */
object NativeTools {

  def alloc(size: NativeSize) = {
    val ptr = new Memory(size.longValue())
    CLibrary.INSTANCE.memset(ptr, 0, size)
    ptr
  }

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
