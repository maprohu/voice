package voice.core.events

import akka.stream.scaladsl.Source
import boopickle.DefaultBasic.PicklerGenerator
import toolbox8.akka.actor.{BoopickleSerializer, Ids, Pickled}


sealed trait LogicalEvent
case class LogicalClick(
  button: ButtonEvent
) extends LogicalEvent
case class LogicalLongClick(
  button: ButtonEvent
) extends LogicalEvent


sealed trait ControllerEvent
sealed trait ButtonEvent
sealed trait JoystickEvent extends LogicalEvent
sealed trait JoystickActiveEvent extends JoystickEvent
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

case class PhysicalEvent(
  event: ControllerEvent
) extends Picky

trait Picky extends Pickled {
  override def booId: Int = Ids.VoiceCore
}

object Picklers {

//  implicit val picklerPhysicalEvent = {
//    import boopickle.Default._
//    PicklerGenerator.generatePickler[PhysicalEvent]
//  }

  def createPickler = {
    import boopickle.Default._

    compositePickler[Pickled]
      .addConcreteType[PhysicalEvent]
  }

  def register = {
    BoopickleSerializer.register(
      Ids.VoiceCore,
      createPickler
    )
  }
}
