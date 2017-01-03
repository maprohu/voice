package voice.sandbox.research

import java.awt.event.{KeyAdapter, KeyEvent, MouseEvent, MouseMotionAdapter}
import java.awt.{BorderLayout, Dimension, Frame, MouseInfo}
import javax.swing.{JFrame, JLabel}

import com.badlogic.gdx.graphics.Color
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.glu.GLU
import com.jogamp.opengl.{GLAutoDrawable, GLEventListener}

/**
  * Created by maprohu on 02-01-2017.
  */
object DisplayJogl {

  case class Sinus(
    freq: Double,
    amp: Double,
    offs: Double = 0
  )

  def sinuses(
    sinus: Sinus*
  ) : LineFunction = {
    val sumAmp = sinus.map(_.amp).sum

    { x =>
      sinus
        .map({ s =>
          import s._
          Math.sin( freq * x - offs ) * amp / sumAmp
        })
        .sum
    }
  }

  case class Spring(
    baseStiffness: Double,
    audioStiffness: Double,
    damping: Double,
    mass: Double,
    color: Color
  )

  case class SpringState(
    time: Double,
    position: Double,
    velocity: Double
  )

  case class Source(
    from: Double,
    to: Double,
    step: Double,
    signal: LineFunction
  ) {
    lazy val rendered = renderLine(
      from,
      to,
      step,
      signal
    )
  }

  def simulate(
    spring: Spring
  )(implicit
    source: Source
  ) : RenderedLine = {
    source
      .rendered
      .scanLeft(
        SpringState(source.from, 0, 0)
      )({ (state, audio) =>
        val (time, audioPosition) = audio

        val baseForce = - state.position * spring.baseStiffness
        val audioForce = (audioPosition - state.position) * spring.audioStiffness
        val dampingForce = - state.velocity * spring.damping
        val timeDifference = time - state.time

        val totalForce = baseForce + audioForce + dampingForce
        val acceleration = totalForce / spring.mass

        val velocityDifference = timeDifference * acceleration
        val positionDifference = timeDifference * state.velocity

        SpringState(
          time = time,
          position = state.position + positionDifference,
          velocity = state.velocity + velocityDifference
        )
      })
      .map({ s =>
        (s.time, s.position)
      })
  }

  def main(args: Array[String]): Unit = {
    import Color._

    val from = 0.0
    val to = 2 * Math.PI
    val step = 10e-4

    val audio =
      sinuses(
        Sinus(1, 10),
        Sinus(20, 1),
        Sinus(200, 1)
      )

    implicit val source = Source(
      from,
      to,
      step,
      audio
    )

    val springs =
      Seq(
        Spring(
          baseStiffness = 0,
          audioStiffness = 40000,
          damping = Math.sqrt(4*40000) / 2,
          mass = 1,
          BLUE
        )
      )

    val simulatedSprings =
      springs
        .map({ s =>
          new LineState(
            simulate(s),
            s.color
          )
        })

    val renderedAudio =
      render(
        from,
        to,
        step,
        Seq(
          Line(
            audio,
            RED
          )
        )
      )

    show(
      from,
      to,
      renderedAudio ++ simulatedSprings
    )
  }

  type LineFunction = Double => Double

  case class Line(
    function: LineFunction,
//    from: Double,
//    to: Double,
    color: Color
  )

  type RenderedLine = Vector[(Double, Double)]

  class LineState(
    val data: RenderedLine,
    val color: Color
  ) {
    var visible = true

    var translate = 0.0

    def toggleVisible = {
      visible = !visible
    }
  }

  def renderLine(
    from: Double,
    to: Double,
    step: Double,
    line: LineFunction
  ) : RenderedLine = {
    Stream
      .iterate(from)(_ + step)
      .takeWhile(_ < to)
      .map({ x =>
        (x, line(x))
      })
      .toVector
  }

  def render(
    from: Double,
    to: Double,
    step: Double,
    lineSeq: Seq[Line]
  ) = {
    lineSeq
      .map({ line =>
        val data = renderLine(
          from,
          to,
          step,
          line.function
        )

        new LineState(
          data,
          line.color
        )
      })
  }

  def show(
    from: Double,
    to: Double,
    lines: Seq[LineState]
  ) = {

    val canvas = new GLCanvas()

    var frameOffset = from
    var frameCount = to - from

    def mouseDeviceX = {
      val componentLocation = canvas.getLocationOnScreen()
      val mouseLocation =
        MouseInfo
          .getPointerInfo
          .getLocation

      mouseLocation.x - componentLocation.x
    }

    def mouseFrame = {
      val x = mouseDeviceX

      (frameCount * x / canvas.getWidth) + frameOffset
    }


    import Color._
//    val colors = Vector(
//      RED,
//      BLUE,
//      GREEN,
//      YELLOW,
//      BROWN,
//      PURPLE
//    )

    var frameMark = 0.0
    var grabbing = Option.empty[Int]

    val frame = new JFrame()
    frame.setSize(new Dimension(600, 400))
    val listener = new GLEventListener {

      import com.jogamp.opengl.GL._

      override def init(drawable: GLAutoDrawable): Unit = {
        val gl = drawable.getGL.getGL2
        val glu = GLU.createGLU(gl)
        gl.glClearColor(0, 0, 0, 0)


      }


      override def display(drawable: GLAutoDrawable): Unit = {
        val gl = drawable.getGL.getGL2
        gl.glClear(GL_COLOR_BUFFER_BIT)
        gl.glLoadIdentity()

        def color(c: Color) = {
          gl.glColor3d(
            c.r,
            c.g,
            c.b
          )
        }


        color(WHITE)
        gl.glBegin(GL_LINES)
        gl.glVertex2d(
          -1, 0
        )
        gl.glVertex2d(
          1, 0
        )
        gl.glEnd()

        val x = mouseDeviceX
        val xl = 2.0 * x / canvas.getWidth - 1.0
        gl.glBegin(GL_LINES)
        gl.glVertex2d(
          xl, 1
        )
        gl.glVertex2d(
          xl, -1
        )
        gl.glEnd()

        // frameOffset -> -1                  => a * fo + b = -1
        // (frameOffset + frameCount) -> 1    => a * (fo+fc) + b = 1

        gl.glTranslated(-1.0 - ( (2.0 * frameOffset) / frameCount ), 0, 0)
        gl.glScaled(2.0 / frameCount, 1, 1)

        lines
          .zipWithIndex
          .filter(_._1.visible)
          .foreach({
            case (fs, idx) =>
              color(
                fs.color
              )
              gl.glPushMatrix()
              gl.glTranslated(fs.translate, 0, 0)

              if (grabbing.exists(_ == idx)) {
                gl.glTranslated(mouseFrame - frameMark, 0, 0)
              }

              gl.glBegin(GL_LINE_STRIP)
              fs
                .data
                .foreach({
                  case (xd, yd) =>
                    gl.glVertex2d(xd, yd)
                })

              gl.glEnd()

              gl.glPopMatrix()
          })

      }

      override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int): Unit = {
        val gl = drawable.getGL.getGL2
        gl.glViewport(x, y, width, height)

      }

      override def dispose(drawable: GLAutoDrawable): Unit = ()
    }
    canvas.addGLEventListener(listener)
    canvas.addMouseMotionListener(
      new MouseMotionAdapter {
        override def mouseMoved(e: MouseEvent): Unit = {
          canvas.repaint()
        }
      }
    )
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setExtendedState(Frame.MAXIMIZED_BOTH)
    frame.getContentPane.setLayout(new BorderLayout())
    frame.getContentPane.add(canvas)

    val status = new JLabel()
    status.setOpaque(true)
    status.setBackground(java.awt.Color.BLACK)
    status.setForeground(java.awt.Color.WHITE)
    frame.getContentPane.add(status, BorderLayout.SOUTH)



    var str = ""

    def updateStatus() = {
      status.setText("> " + str)
    }
    updateStatus()


    type Handler = String => Boolean

    val LineMovePattern = "cs([1-9])".r
    val LineGrabPattern = "ga([1-9])".r

    val defaultHandler : Handler = { str =>
      var handled = true
      str match {
        case "vd" =>
          frameOffset = 0
          frameCount = to - from
          canvas.repaint()

        case "va" =>
          val frameEnd = frameOffset + frameCount
          frameOffset = mouseFrame
          frameCount = frameEnd - frameOffset
          canvas.repaint()

        case "vs" =>
          frameCount = mouseFrame - frameOffset
          canvas.repaint()

        case "ca" =>
          frameMark = mouseFrame


        case LineMovePattern(n) =>
          val moveAmount = mouseFrame - frameMark
          val i = n(0) - '1'
          if (i < lines.length) {
            lines(i).translate += moveAmount
            canvas.repaint()
          }

        case LineGrabPattern(n) =>
          frameMark = mouseFrame
          val i = n(0) - '1'
          if (i < lines.length) {
            grabbing = Some(i)
          }

        case "gs" =>
          grabbing
            .foreach({ i =>
              lines(i).translate += mouseFrame - frameMark
            })
          grabbing = None


        case "0" =>
          lines.foreach(_.visible = false)
          canvas.repaint()

        case n if "1" <= n && n <= "9" =>
          val i = n(0) - '1'
          if (i < lines.length) {
            lines(i).toggleVisible
            canvas.repaint()
          }
        case _ =>
          handled = false
      }

      handled
    }

    var handler : Handler = defaultHandler

    frame
      .addKeyListener(
        new KeyAdapter {
          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
              str = ""
            }
            updateStatus()
          }
          override def keyTyped(e: KeyEvent): Unit = {
            val ch = e.getKeyChar

            if (32 <= ch && ch < 127) {
              str += ch

              val handled = handler(str)
              if (handled) {
                str = ""
              }
            }

            updateStatus()
          }

        }
      )


    frame.setVisible(true)


  }

}

/*

hearing range: 20Hz -> 18kHz
speech range: 100Hz -> 8kHz


params:

minimum frequency
maximum frequency
spring count
period count: how many periods of time to keep count for (for each spring) ~ 10?

look for stable periods

omega = frequency
k = spring stiffness
m = mass
resonant frequency: omega = sqrt( k / m )
damping frequency = sqrt(4 * k * m)



 */