package voice.testing

import java.awt.event.KeyEvent
import java.awt.{KeyEventDispatcher, KeyboardFocusManager}
import java.io.{File, PipedInputStream, PipedOutputStream}
import javax.swing.JFrame

import toolbox8.jartree.logging.LoggingSetup
import voice.core.SingleMixer.PlayableSound
import voice.core.{HidParser, NatoAlphabet, SingleMixer, VoiceLogic}

/**
  * Created by maprohu on 17-11-2016.
  */
object RunHidEmulator {

  @volatile var os = new PipedOutputStream()

  def connect = synchronized {
    val os = new PipedOutputStream()
    val is = new PipedInputStream()
    is.connect(os)

    RunHidEmulator.os = os

    is
  }

  def main(args: Array[String]): Unit = {
    LoggingSetup.configureLogging("voicer", true)

    import scala.concurrent.ExecutionContext.Implicits.global

    VoiceLogic.run(
      new File("../voice/target/hidemudb"),
      { () => connect }
    )

    val buffer = Array.ofDim[Byte](3)
    buffer(0) = HidParser.FirstByteConstantValue

    def write(v: Int) = {
//      println(v)
      buffer(1) = (v & 0xFF).toByte
      buffer(2) = ((v >> 8) & 0xFF).toByte
      os.write(buffer)
    }


    KeyboardFocusManager
      .getCurrentKeyboardFocusManager
      .addKeyEventDispatcher(
        new KeyEventDispatcher {
          override def dispatchKeyEvent(e: KeyEvent): Boolean = {
//            println(e)
            e.getID match {
              case KeyEvent.KEY_PRESSED =>
                import voice.core.HidParser.KeyCodes._
                e.getKeyCode match {
                  case KeyEvent.VK_UP =>
                    write(buttonC)
                  case KeyEvent.VK_DOWN =>
                    write(buttonD)
                  case KeyEvent.VK_LEFT =>
                    write(buttonB)
                  case KeyEvent.VK_RIGHT =>
                    write(buttonA)
                  case KeyEvent.VK_8 =>
                    write(joystickUp)
                  case KeyEvent.VK_2 =>
                    write(joystickDown)
                  case KeyEvent.VK_4 =>
                    write(joystickLeft)
                  case KeyEvent.VK_6 =>
                    write(joystickRight)
                  case KeyEvent.VK_7 =>
                    write(joystickUpLeft)
                  case KeyEvent.VK_9 =>
                    write(joystickUpRight)
                  case KeyEvent.VK_3 =>
                    write(joystickDownRight)
                  case KeyEvent.VK_1 =>
                    write(joystickDownLeft)
                  case KeyEvent.VK_MINUS =>
                    write(buttonHigh)
                  case KeyEvent.VK_PLUS =>
                    write(buttonLow)
                }
              case KeyEvent.KEY_RELEASED =>
                write(HidParser.KeyCodes.released)
              case _ =>
            }

//            (e.getID, e.getKeyChar) match {
//              case (KeyEvent.KEY_PRESSED, c) =>
//                println(e)
//              case _ =>
//            }
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
