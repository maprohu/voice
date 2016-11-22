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
      case 0x0005 => Released
      case 0x0105 => ButtonA
      case 0x0015 => ButtonB
      case 0x0085 => ButtonC
      case 0x0025 => ButtonD
      case 0x0405 => ButtonLow
      case 0x0805 => ButtonHigh
      case 0x0009 => JoystickLeft
      case 0x0001 => JoystickRight
      case 0x0006 => JoystickDown
      case 0x0004 => JoystickUp
      case 0x000a => JoystickDownLeft
      case 0x0002 => JoystickDownRight
      case 0x0008 => JoystickUpLeft
      case 0x0000 => JoystickUpRight
      case _ => Unknown
    }
  }
}
