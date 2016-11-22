package voice.core

import java.nio.file.Paths

import voice.core.events._

/**
  * Created by maprohu on 21-11-2016.
  */
object HidParser {

  val HidFileName = "bt4ok"
  val DevPath = Paths.get("/dev")
  val HidFilePath = DevPath.resolve(HidFileName)

  val FirstByteConstantValue : Byte = 4

  def decodePhysical(
    b1: Byte,
    b2: Byte
  ) : ControllerEvent = {
    val bits = (b2 << 8) | (b1 & 0xFF)

    bits match {
      case KeyCodes.released => Released
      case KeyCodes.buttonA => ButtonA
      case KeyCodes.buttonB => ButtonB
      case KeyCodes.buttonC => ButtonC
      case KeyCodes.buttonD => ButtonD
      case KeyCodes.buttonLow => ButtonLow
      case KeyCodes.buttonHigh => ButtonHigh
      case KeyCodes.joystickLeft => JoystickLeft
      case KeyCodes.joystickRight => JoystickRight
      case KeyCodes.joystickDown => JoystickDown
      case KeyCodes.joystickUp => JoystickUp
      case KeyCodes.joystickDownLeft => JoystickDownLeft
      case KeyCodes.joystickDownRight => JoystickDownRight
      case KeyCodes.joystickUpLeft => JoystickUpLeft
      case KeyCodes.joystickUpRight => JoystickUpRight
      case _ => Unknown
    }
  }

  object KeyCodes {
    val released = 0x0005
    val buttonA = 0x0105
    val buttonB = 0x0015
    val buttonC = 0x0085
    val buttonD = 0x0025
    val buttonLow = 0x0405
    val buttonHigh = 0x0805
    val joystickLeft = 0x0009
    val joystickRight = 0x0001
    val joystickDown = 0x0006
    val joystickUp = 0x0004
    val joystickDownLeft = 0x000a
    val joystickDownRight = 0x0002
    val joystickUpLeft = 0x0008
    val joystickUpRight = 0x0000
  }
}
