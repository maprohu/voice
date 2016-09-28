package voice.sandbox

import java.awt.Frame
import javax.swing.JFrame

import com.sun.media.sound.FFT
import org.jtransforms.fft.DoubleFFT_1D
import org.math.plot.Plot2DPanel

/**
  * Created by martonpapp on 28/09/16.
  */
object RunHowtoFft {

  def main(args: Array[String]): Unit = {

    val SampleSize = 2 << 10


    val x =
      (0 until SampleSize)
        .map(i => 2 * math.Pi * i / SampleSize)
        .toArray

    val y =
      x.map(x => math.sin(x) + math.sin(100 * x) / 2)

    val plot = new Plot2DPanel()


//    plot.addLinePlot("sine", x, y)


    val jfft = new DoubleFFT_1D(SampleSize)
    jfft.realForward(y)

//    val fft = new FFT(SampleSize/2, -1)
//    fft.transform(y)

    val f =
      y
        .take(SampleSize)
        .grouped(2)
        .map(_.toSeq)
        .map({
          case Seq(re, im) =>
            math.sqrt(re*re + im*im) / SampleSize
        })
        .toArray

    val n =
      (0 until SampleSize / 2)
        .map(_.toDouble)
        .toArray

    plot.addLinePlot(
      "fourier",
      n,
      f
    )






    val frame = new JFrame("sine")
    frame.setContentPane(plot)
    frame.setExtendedState(Frame.MAXIMIZED_BOTH)
    frame.setVisible(true)

  }

}
