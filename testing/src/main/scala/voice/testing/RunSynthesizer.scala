package voice.testing

import java.awt.event.KeyEvent
import java.awt.{Frame, KeyEventDispatcher, KeyboardFocusManager}
import javax.swing.{JFrame, WindowConstants}

/**
  * Created by maprohu on 17-11-2016.
  */
object RunSynthesizer {

  def main(args: Array[String]): Unit = {
    KeyboardFocusManager
      .getCurrentKeyboardFocusManager
      .addKeyEventDispatcher(
        new KeyEventDispatcher {
          override def dispatchKeyEvent(e: KeyEvent): Boolean = {
            println(e)
            false
          }
        }
      )

    val jf = new JFrame()
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    jf.setExtendedState(Frame.MAXIMIZED_BOTH)
    jf.setVisible(true)

  }

}
