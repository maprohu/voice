package voice.core.events



sealed trait LogicalEvent
case class LogicalClick(
  button: ButtonEvent
) extends LogicalEvent
case class LogicalLongClick(
  button: ButtonEvent
) extends LogicalEvent


sealed trait DownEvent
sealed trait ControllerEvent
sealed trait ButtonEvent extends DownEvent
sealed trait JoystickEvent extends LogicalEvent
sealed trait JoystickActiveEvent extends JoystickEvent with DownEvent
case object ButtonA extends ButtonEvent with ControllerEvent
case object ButtonB extends ButtonEvent with ControllerEvent
case object ButtonC extends ButtonEvent with ControllerEvent
case object ButtonD extends ButtonEvent with ControllerEvent
case object ButtonHigh extends ButtonEvent with ControllerEvent
case object ButtonLow extends ButtonEvent with ControllerEvent
case object JoystickUp extends JoystickActiveEvent with ControllerEvent
case object JoystickDown extends JoystickActiveEvent with ControllerEvent
case object JoystickLeft extends JoystickActiveEvent with ControllerEvent
case object JoystickRight extends JoystickActiveEvent with ControllerEvent
case object JoystickUpLeft extends JoystickActiveEvent with ControllerEvent
case object JoystickDownLeft extends JoystickActiveEvent with ControllerEvent
case object JoystickUpRight extends JoystickActiveEvent with ControllerEvent
case object JoystickDownRight extends JoystickActiveEvent with ControllerEvent
case object Released extends ControllerEvent with JoystickEvent
case object Unknown extends ControllerEvent


