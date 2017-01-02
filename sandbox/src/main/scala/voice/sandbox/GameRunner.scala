package voice.sandbox

import java.awt.{Canvas, Frame}
import javax.sound.sampled.{AudioFormat, AudioSystem}
import javax.swing.JFrame

import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera}
import org.jtransforms.fft.FloatFFT_1D


object GameRunner {

  def run(
    logicProvider: () => GameLogic,
    customizer: LwjglApplication => Unit = _ => ()
  ) {

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

    val frame = new JFrame()
    val canvas = new Canvas()
    frame.add(canvas)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE )
    frame.setExtendedState(Frame.MAXIMIZED_BOTH)
    frame.setVisible(true)
    val app = new LwjglApplication(listener, canvas)
    customizer(app)
  }

}



trait GameLogic {
  def render : Unit
  def resize(width: Int, height: Int): Unit = ()
}

