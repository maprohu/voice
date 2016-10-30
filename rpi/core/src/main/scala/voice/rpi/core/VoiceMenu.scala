package voice.rpi.core

import java.awt.Menu

import akka.stream.scaladsl.Sink
import voice.rpi.core.VoiceHid.{LogicalEvent, Running}
import voice.rpi.core.VoiceMenu.Menu

import scala.collection.immutable._
import scala.concurrent.Future

/**
  * Created by maprohu on 30-10-2016.
  */
object VoiceMenu {

  case class Menu(
    name: String,
    parent: Option[Menu],
    items: Seq[Item]
  )

  trait Item {
    def name: String
  }

  case class State(
    next: LogicalEvent => Future[State]
  )

  def sink(
    running: Running
  ) = {
    def announce(item: Item) = {
      running
        .voiceController
        .talker
        .cached(
          item.name
        )
    }

    Sink
      .foldAsync[State, LogicalEvent](
        State()
      )({ (state, event) =>
    })

  }

}

object Menus {

  lazy val Main = Menu(
    name = "main menu",
    parent = None,
    items = Seq(

    )
  )

}

