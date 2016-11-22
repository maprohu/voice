package voice.testing

import toolbox8.akka.actor.Pickled
import voice.akka.{PhysicalEvent, Picklers}
import voice.core.events._

/**
  * Created by maprohu on 11-11-2016.
  */
object RunBooPickler {

  def main(args: Array[String]): Unit = {
    val event = PhysicalEvent(
      ButtonHigh
    )

    implicit val pickler = Picklers.createPickler
    import boopickle.DefaultBasic._
//
//    implicit val pickler = compositePickler[Pickled]
//    pickler
//      .addConcreteType[PhysicalEvent]

    Pickle.intoBytes[Pickled](event)


  }

}
