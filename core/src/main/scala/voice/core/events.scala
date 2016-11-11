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
trait ButtonEvent extends ControllerEvent
trait JoystickEvent extends ControllerEvent with LogicalEvent
trait JoystickActiveEvent extends JoystickEvent
case object ButtonA extends ButtonEvent
case object ButtonB extends ButtonEvent
case object ButtonC extends ButtonEvent
case object ButtonD extends ButtonEvent
case object ButtonHigh extends ButtonEvent
case object ButtonLow extends ButtonEvent
case object JoystickUp extends JoystickActiveEvent
case object JoystickDown extends JoystickActiveEvent
case object JoystickLeft extends JoystickActiveEvent
case object JoystickRight extends JoystickActiveEvent
case object JoystickUpLeft extends JoystickActiveEvent
case object JoystickDownLeft extends JoystickActiveEvent
case object JoystickUpRight extends JoystickActiveEvent
case object JoystickDownRight extends JoystickActiveEvent
case object Released extends ControllerEvent with JoystickEvent
case object Unknown extends ControllerEvent

case class PhysicalEvent(
  event: ControllerEvent
) extends Picky

trait Picky extends Pickled {
  override def booId: Int = Ids.VoiceCore
}

object Picklers {

  implicit val picklerPhysicalEvent = {
    import boopickle.Default._
    PicklerGenerator.generatePickler[PhysicalEvent]
  }

  def register = {
    val pickler = {
      import boopickle.DefaultBasic._
      compositePickler[Pickled]
        .addConcreteType[PhysicalEvent]
    }

    BoopickleSerializer.register(
      Ids.VoiceCore,
      pickler
    )
  }
}
