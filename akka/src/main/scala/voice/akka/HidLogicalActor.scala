package voice.akka

import akka.actor.{Actor, ActorRef}
import FeedbackActor.InvalidPhysicalInput
import voice.core.events._

import scala.concurrent.Promise

/**
  * Created by maprohu on 04-11-2016.
  */
class HidLogicalActor(
  config: HidLogicalActor.Config
) extends Actor {
  import config._
  import HidLogicalActor._
  import VoiceParser._
  import context.dispatcher

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
    output ! Released
    state = Normal
  }

  override def receive: Receive = {
    case p : PhysicalEvent => import p._
      (state, event) match {
        case _ if state.latest == event =>
          // ignore repetition

        case (Normal, b : ButtonEvent with ControllerEvent) =>
          val promise = Promise[LogicalEvent]()

          promise
            .future
            .foreach(e => output ! e)

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

        case (Normal, j : JoystickActiveEvent with ControllerEvent) =>
          output ! j
          state = Joystick(j)

        case (_ : Joystick, Released) =>
          releaseJoystick()

        case _ =>
          feedback !
            InvalidPhysicalInput(
              state,
              event
            )

          state match {
            case s : Button =>
              shortClick(s)
            case _ : Joystick =>
              releaseJoystick()
            case Normal =>
          }

      }
  }
}

object HidLogicalActor {
  case class Config(
    output: ActorRef,
    feedback: ActorRef
  )

  sealed trait State {
    val latest: ControllerEvent
  }

  case object Normal extends State {
    val latest = Released
  }

  case class Joystick(
    latest: ControllerEvent with JoystickEvent
  ) extends State

  case class Button(
    button: ButtonEvent with ControllerEvent,
    promise: Promise[LogicalEvent]
  ) extends State {
    val latest = button
  }

}
