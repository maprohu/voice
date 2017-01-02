package voice.sandbox.training

import java.awt.event.{KeyAdapter, KeyEvent, MouseEvent, MouseMotionAdapter}
import java.awt.geom.Line2D
import java.awt.{BorderLayout, Dimension, Frame, MouseInfo}
import javax.swing.{JFrame, JLabel, JPanel}

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color._
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.glu.GLU
import com.jogamp.opengl.{GLAutoDrawable, GLEventListener}
import voice.sandbox.GameRunner

/**
  * Created by maprohu on 02-01-2017.
  */
object DisplayJogl {

  def main(args: Array[String]): Unit = {
    import voice.storage.Vowels._
    show(RunTrainingVowelDisplay.data(
      I
    ))
  }

  class Line(
    val data : Array[(Double, Double)]
  ) {
    var visible = true

    var translate = 0

    def toggleVisible = {
      visible = !visible
    }
  }

  def show(data: Seq[Vector[Array[Byte]]]) = {
    val shorts = Display.toShort(data)

    val lines =
      shorts
        .map({ sa =>
          sa
            .map({ ys =>
              ys / -(Short.MinValue.toDouble)
            })
            .zipWithIndex
            .map({ case (yd, xi) => (xi.toDouble, yd)})
        })
        .map(a => new Line(a))

    val canvas = new GLCanvas()

    val maxLength = lines.map(_.data.length).max
    var frameOffset = 0
    var frameCount = maxLength

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
    val colors = Vector(
      RED,
      BLUE,
      GREEN,
      YELLOW,
      BROWN,
      PURPLE
    )

    var frameMark = 0
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
                colors(idx % colors.length)
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
          frameCount = maxLength
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
