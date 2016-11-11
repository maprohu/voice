package voice.core

import events._
import voice.audio.Talker
import voice.core.VoiceParser._

/**
  * Created by maprohu on 30-10-2016.
  */
class VoiceController(
  val talker: Talker
) {

  def name(button: ButtonEvent) = {
    button match {
      case ButtonA => "A."
      case ButtonB => "b"
      case ButtonC => "c"
      case ButtonD => "d"
      case ButtonLow => "low"
      case ButtonHigh => "high"
      case _ => "click"
    }
  }

  def name(button: JoystickActiveEvent) = {
    button match {
      case JoystickDown => "down"
      case JoystickUp => "up"
      case JoystickLeft => "left"
      case JoystickRight => "right"
      case JoystickUpLeft => "up left"
      case JoystickUpRight => "up right"
      case JoystickDownLeft => "down left"
      case JoystickDownRight => "down right"
      case _ => ???
    }
  }

  def feedback(
    logicalEvent: LogicalEvent
  ) = {
    logicalEvent match {
      case Released =>
        // no feedback
      case LogicalClick(c) =>
        talker.cached(name(c))
      case LogicalLongClick(c) =>
        talker.cached(s"long ${name(c)}")
      case e : JoystickActiveEvent =>
        talker.cached(name(e))
      case _ =>
        talker.error()
    }
  }

}
