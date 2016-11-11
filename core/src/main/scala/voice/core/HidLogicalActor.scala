package voice.core

import events._
import akka.actor.Actor
import toolbox8.akka.actor.Target
import voice.core.FeedbackActor.InvalidPhysicalInput

import scala.concurrent.Promise

/**
  * Created by maprohu on 04-11-2016.
  */
class HidLogicalActor extends Actor {
  import HidLogicalActor._
  import VoiceParser._
  import context.dispatcher

  val out = Target()
  val feedback = Target()

  var state : State = Normal

  def shortClick(
    s: Button
  ) = {
    s
      .promise
      .trySuccess(
        LogicalClick(s.button)
      )

    state = Normal
  }

  def releaseJoystick() = {
    out.send(Released)
    state = Normal
  }

  override def receive: Receive = {
    case PhysicalEvent(event : ControllerEvent) =>
      (state, event) match {
        case (Normal, b : ButtonEvent) =>
          val promise = Promise[LogicalEvent]()

          promise
            .future
            .foreach(out.send)

          context
            .system
            .scheduler
            .scheduleOnce(
              LongClickDuration
            ) {
              promise.trySuccess(
                LogicalLongClick(b)
              )
            }

          state = Button(
            b,
            promise
          )

        case (s: Button, Released) =>
          shortClick(s)

        case (Normal, j : JoystickActiveEvent) =>
          out.send(j)
          state = Joystick

        case (Joystick, Released) =>
          releaseJoystick()

        case _ =>
          feedback.send(
            InvalidPhysicalInput(
              state,
              event
            )
          )

          state match {
            case s : Button =>
              shortClick(s)
            case Joystick =>
              releaseJoystick()
            case Normal =>
          }

      }
  }
}

object HidLogicalActor {
  import VoiceParser._

  sealed trait State

  case object Normal extends State

  case object Joystick extends State

  case class Button(
    button: ButtonEvent,
    promise: Promise[LogicalEvent]
  ) extends State

}
