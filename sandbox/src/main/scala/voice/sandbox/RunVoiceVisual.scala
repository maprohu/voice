package voice.sandbox

import javax.sound.sampled.{AudioFormat, AudioSystem}

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera}
import org.jtransforms.fft.FloatFFT_1D


object RunVoiceVisual {


  def logicProvider() : GameLogic = new AudioLogic(100)

  def main(args: Array[String]) {

    val listener = new ApplicationAdapter {
      var logic : GameLogic = null
      override def create(): Unit = {
        logic = logicProvider()
      }

      override def render(): Unit = {
        logic.render
      }

      override def resize(width: Int, height: Int): Unit = {
        logic.resize(width, height)
      }
    }

    val app = new LwjglApplication(listener)
  }

}



trait GameLogic {
  def render : Unit
  def resize(width: Int, height: Int): Unit = ()
}




class AudioLogic(
  frameRate : Float = 1/60f
) extends GameLogic {

  val BufferSize = 2 << 11
  val BufferSizeSqr = BufferSize * BufferSize

  val Format = new AudioFormat(44100.0f, 16, 1, true, true)

  val MixerInfo =
    AudioSystem
      .getMixerInfo
      .find(_.getName.toLowerCase.contains("microphone"))
      .get

  val line = AudioSystem.getTargetDataLine(Format, MixerInfo)

  line.open(Format)
  line.start()

  val samples = Array.fill(BufferSize)(0)
  val samplesCopy = samples.clone()
  val samplesCopy2 = samples.clone()
  var samplePointer = 0

  def writeSample(sample: Int) : Unit = synchronized {
    samples(samplePointer) = sample
    samplePointer += 1
    if (samplePointer == BufferSize) samplePointer = 0
  }

  def readSamples = {
    readSamplesTo(samplesCopy)
  }
  def readSamples2 = {
    readSamplesTo(samplesCopy2)
  }

  def readSamplesTo(a: Array[Int]) : Int = synchronized {
    System.arraycopy(
      samples,
      0,
      a,
      0,
      BufferSize
    )

    samplePointer
  }

  new Thread() {
    override def run(): Unit = {
      val buffer = Array[Byte](0, 0)
      var numRead = 0
      do {
        writeSample(
          (buffer(0).toInt << 8) | (buffer(1) & 0xff)
        )

        numRead = line.read(buffer, 0, buffer.length)
      } while (numRead != 0)
    }
  }.start()

  val spectrum = Array.ofDim[Float](BufferSize)

  new Thread() {
    val fft = new FloatFFT_1D(BufferSize)
    val b = Array.ofDim[Float](BufferSize)

    override def run(): Unit = {
      while (true) {
        val p = readSamples2
        var pv = p
        var idx = 0
        do {
          b(idx) = samplesCopy2(pv).toFloat / MaxSample
          idx += 1
          pv += 1
          if (pv == BufferSize) pv = 0
        } while (pv != p)

        fft.realForward(b)

        spectrum.synchronized {
          var i = 0
          var t = 0
          do {
            val re = b(i)
            val im = b(i+1)
            spectrum(t) = (re*re + im*im) / BufferSize
            i += 2
            t += 1
          } while (i < BufferSize)
        }

      }


    }
  }.start()


  val camera = new OrthographicCamera()

  val MaxSample = 0xFFFF / 2
  val MaxSample2 = MaxSample * MaxSample

  val ColumnWidth = 2.0f / BufferSize

  val sr = new ShapeRenderer()

  def render = {
    Gdx.gl.glClearColor( 0, 0, 1, 1 )
    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT )


    sr.setColor(Color.WHITE)
    sr.setProjectionMatrix(camera.combined)

    sr.begin(ShapeType.Line)

    val pointer = readSamples
    var p = pointer
    var c0 = -1f
    var y0 = 0f
    var y1 = 0f

    def stepPointer : Unit = {
      p += 1
      if (p == BufferSize) p = 0
      y1 = samplesCopy(p).toFloat / MaxSample
    }

    stepPointer
    y0 = y1

    do {
      stepPointer
      val c1 = c0 + ColumnWidth

      sr.line(
        c0,
        y0,
        c1,
        y1
      )

      c0 = c1
      y0 = y1

    } while (p != pointer)

    spectrum.synchronized {
      var i = 0
      var c = -1f

      do {
        sr.line(
          c,
          -1f + spectrum(i),
          c,
          -1f
        )

        i += 1
        c += ColumnWidth

      } while (i < BufferSize)
    }


    sr.end()

  }


}

