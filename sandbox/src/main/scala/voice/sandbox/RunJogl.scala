package voice.sandbox

import java.awt.event.{MouseEvent, MouseMotionAdapter}
import java.awt.{Dimension, Frame, MouseInfo}
import javax.swing.JFrame

import com.jogamp.opengl.{GLAutoDrawable, GLEventListener}
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.glu.GLU

/**
  * Created by maprohu on 02-01-2017.
  */
object RunJogl {

  def main(args: Array[String]): Unit = {

    val canvas = new GLCanvas()

    def mouseDeviceX = {
      val componentLocation = canvas.getLocationOnScreen()
      val mouseLocation =
        MouseInfo
          .getPointerInfo
          .getLocation

      mouseLocation.x - componentLocation.x
    }

    val frame = new JFrame()
    frame.setSize(new Dimension(600, 400))
    val listener = new GLEventListener {

      import com.jogamp.opengl.GL._
      import com.jogamp.opengl.GL2._

      override def init(drawable: GLAutoDrawable): Unit = {
        val gl = drawable.getGL.getGL2
        val glu = GLU.createGLU(gl)
        gl.glClearColor(0, 0, 0, 0)


      }

      override def display(drawable: GLAutoDrawable): Unit = {
        val gl = drawable.getGL.getGL2
        gl.glClear(GL_COLOR_BUFFER_BIT)
        gl.glLoadIdentity()

        gl.glBegin(GL_TRIANGLES)
        gl.glVertex2d(
          0, 0
        )
        gl.glVertex2d(
          0.5, 0.5
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
    frame.getContentPane.add(canvas)
    frame.setVisible(true)


  }

}
