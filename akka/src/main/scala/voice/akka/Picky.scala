package voice.akka

import toolbox8.akka.actor.{BoopickleSerializer, Ids, Pickled}
import voice.core.events.ControllerEvent



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