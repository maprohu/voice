package com.sun.jna

import java.nio.ByteBuffer

/**
  * Created by pappmar on 21/12/2016.
  */
object JnaTools {

  def string(
    str: String
  ) = new NativeString(str)

  def stringBuffer(
    str: String
  ) = {
    ByteBuffer.wrap(Native.getBytes(str))
  }



}
