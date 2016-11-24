package voice.testing

import java.awt.event.KeyEvent
import java.awt.{KeyEventDispatcher, KeyboardFocusManager}
import java.io.{File, PipedInputStream, PipedOutputStream}
import java.nio.ByteBuffer
import java.nio.channels.Pipe
import javax.swing.JFrame

import com.typesafe.scalalogging.StrictLogging
import toolbox8.jartree.logging.LoggingSetup
import voice.core.HidParser.KeyCodes
import voice.core.SingleMixer.PlayableSound
import voice.core.{HidParser, NatoAlphabet, SingleMixer, VoiceLogic}

/**
  * Created by maprohu on 17-11-2016.
  */
object RunHidEmulator extends StrictLogging {


//  @volatile var os = new PipedOutputStream()
  @volatile var os : Pipe.SinkChannel = null

  def connect = synchronized {
    val pipe = Pipe.open()
//    val os = new PipedOutputStream()
//    val is = new PipedInputStream()
//    is.connect(os)



    RunHidEmulator.os = pipe.sink()

    pipe.source()
  }

  def main(args: Array[String]): Unit = {
    LoggingSetup.configureLogging("voicer", true)

    import scala.concurrent.ExecutionContext.Implicits.global

    val running = VoiceLogic.run(
      new File("../voice/target/hidemudb"),
      { () => connect }
    )

    VoiceLogic.shutdownAction = { () =>
      running.cancel()
      logger.info("exiting emu vm")
      System.exit(0)
    }

    val buffer = Array.ofDim[Byte](3)
    val byteBuffer = ByteBuffer.wrap(buffer)
    buffer(0) = HidParser.FirstByteConstantValue


    def write(v: Int) = {
//      println(v)
      buffer(1) = (v & 0xFF).toByte
      buffer(2) = ((v >> 8) & 0xFF).toByte
      os.write(byteBuffer)
      byteBuffer.flip()
    }


    KeyboardFocusManager
      .getCurrentKeyboardFocusManager
      .addKeyEventDispatcher(
        new KeyEventDispatcher {
          override def dispatchKeyEvent(e: KeyEvent): Boolean = {
//            println(e)
            import voice.core.HidParser.KeyCodes._
            def handleKey(v: Int) = {
              e.getID match {
                case KeyEvent.KEY_PRESSED =>
                  write(v)
                case KeyEvent.KEY_RELEASED =>
                  write(released)
                case _ =>
              }
            }

            e.getKeyCode match {
              case KeyEvent.VK_UP =>
                handleKey(buttonC)
              case KeyEvent.VK_DOWN =>
                handleKey(buttonD)
              case KeyEvent.VK_LEFT =>
                handleKey(buttonB)
              case KeyEvent.VK_RIGHT =>
                handleKey(buttonA)
              case KeyEvent.VK_NUMPAD8 =>
                handleKey(joystickUp)
              case KeyEvent.VK_NUMPAD2 =>
                handleKey(joystickDown)
              case KeyEvent.VK_NUMPAD4 =>
                handleKey(joystickLeft)
              case KeyEvent.VK_NUMPAD6 =>
                handleKey(joystickRight)
              case KeyEvent.VK_NUMPAD7 =>
                handleKey(joystickUpLeft)
              case KeyEvent.VK_NUMPAD9 =>
                handleKey(joystickUpRight)
              case KeyEvent.VK_NUMPAD3 =>
                handleKey(joystickDownRight)
              case KeyEvent.VK_NUMPAD1 =>
                handleKey(joystickDownLeft)
              case KeyEvent.VK_SUBTRACT =>
                handleKey(buttonHigh)
              case KeyEvent.VK_ADD =>
                handleKey(buttonLow)
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
