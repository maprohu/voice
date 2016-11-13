package voice.core

import java.lang.Math

import org.jtransforms.fft.FloatFFT_1D

/**
  * Created by maprohu on 13-11-2016.
  */
object RunTestFFT {

  val SampleSize = 7

  val fft = new FloatFFT_1D(SampleSize)
//  val Buffer = Array.ofDim[Float](SampleSize)

  def process(fn: Float => Float) = {
    val fa =
      (0 until SampleSize)
        .map({ n =>
          val x = 2 * math.Pi * n / SampleSize

          fn(x.toFloat)
        })
        .toArray



    println(fa.toSeq)
    val fa2 = fa ++ Iterator.continually(0f).take(SampleSize)
    fft.realForward(fa)
    fft.realForwardFull(fa2)
    println(fa.toSeq)
    println(fa2.toSeq)
//    val s1 = fa2.toSeq
////        .map(_ / SampleSize * 2)
//
//    println(
//      s1
//    )
//    println(
//      s1
//        .grouped(2)
//        .map(_.toSeq)
//        .collect({ case Seq(re, im) =>
//            math.sqrt(re*re + im*im)
//        })
//        .toVector
//
//    )


  }

  def main(args: Array[String]): Unit = {
    import math._
    process(x => {
      cos(3*x)
    }.toFloat)





  }

}
