package voice.testing

import com.jsyn.JSyn
import com.jsyn.devices.AudioDeviceManager
import com.jsyn.unitgen.{LineOut, SineOscillator}

import scala.io.StdIn

/**
  * Created by maprohu on 30-10-2016.
  */
object RunJSyn {

  def main(args: Array[String]): Unit = {
    val syn = JSyn.createSynthesizer()
    syn.start(
      44100,
      AudioDeviceManager.USE_DEFAULT_DEVICE,
      2,
      AudioDeviceManager.USE_DEFAULT_DEVICE,
      2
    )

    val osc = new SineOscillator()
    syn.add(osc)

    val out = new LineOut()
    syn.add(out)

    osc.output.connect(out)

    osc.frequency.set(345.0)
    osc.amplitude.set(0.6)

    out.start()

    StdIn.readLine()


    syn.stop()
  }

}
