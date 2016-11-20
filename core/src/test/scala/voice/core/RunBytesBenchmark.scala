package voice.core

import java.nio.ByteBuffer

/**
  * Created by maprohu on 20-11-2016.
  */
object RunBytesBenchmark {

  val Repeat = 10
  val Count = 100000000

  def main(args: Array[String]): Unit = {

    (0 until Repeat)
      .foreach { r =>

        val startBB = System.currentTimeMillis()
        (0 until Count)
          .foreach { v =>

            ByteBuffer
              .allocate(java.lang.Long.BYTES)
              .putLong(v)
              .array()

          }
        val endBB = System.currentTimeMillis()

        println(s"bb: ${endBB - startBB}")


        val startBits = System.currentTimeMillis()
        (0 until Count)
          .foreach { v =>

            Array[Byte](
              ((v >> 56) & 0xFF).toByte,
              ((v >> 48) & 0xFF).toByte,
              ((v >> 40) & 0xFF).toByte,
              ((v >> 32) & 0xFF).toByte,
              ((v >> 24) & 0xFF).toByte,
              ((v >> 16) & 0xFF).toByte,
              ((v >> 8) & 0xFF).toByte,
              ((v) & 0xFF).toByte
            )
          }
        val endBits = System.currentTimeMillis()

        println(s"bits: ${endBits - startBits}")



      }




  }

}
