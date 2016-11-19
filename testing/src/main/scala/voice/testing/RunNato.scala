package voice.testing

import java.awt.event.KeyEvent
import java.awt.{Frame, KeyEventDispatcher, KeyboardFocusManager}
import javax.swing.JFrame

import voice.core.SingleMixer.PlayableSound
import voice.core.{NatoAlphabet, SingleMixer, WaveFile}

/**
  * Created by maprohu on 17-11-2016.
  */
object RunNato {

  var cache = Map.empty[Char, PlayableSound]

  def main(args: Array[String]): Unit = {
    val mixer = SingleMixer()

    KeyboardFocusManager
      .getCurrentKeyboardFocusManager
      .addKeyEventDispatcher(
        new KeyEventDispatcher {
          override def dispatchKeyEvent(e: KeyEvent): Boolean = {
            (e.getID, e.getKeyChar) match {
              case (KeyEvent.KEY_PRESSED, c) if ('a' <= c) && (c <= 'z') =>
                cache
                  .get(c)
                  .getOrElse({
                    val p = mixer.render(NatoAlphabet.load(c))
                    cache = cache.updated(c, p)
                    p
                  })
                  .play

                println(c)
              case _ =>
            }
            false
          }
        }
      )

    val jf = new JFrame()
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
//    jf.setExtendedState(Frame.MAXIMIZED_BOTH)
    jf.setSize(100, 100)
    jf.setVisible(true)

  }

}
