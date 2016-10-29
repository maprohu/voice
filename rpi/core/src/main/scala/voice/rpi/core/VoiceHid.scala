package voice.rpi.core

import java.io.FileInputStream

import akka.event.Logging
import akka.stream.{Attributes, Materializer}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source, StreamConverters}
import akka.util.ByteString
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import monix.execution.Cancelable

import scala.collection.immutable._

/**
  * Created by maprohu on 29-10-2016.
  */
class VoiceHid(
  implicit materializer: Materializer
) {

  private val (stopper, publisher) =
    StreamConverters
      .fromInputStream(
        () => new FileInputStream("/dev/hidraw0")
      )
      .mergeMat(
        Source.maybe
      )(Keep.right)
      .log("hid").withAttributes(Attributes.logLevels(onFinish = Logging.InfoLevel, onFailure = Logging.InfoLevel))
      .toMat(
        Sink.asPublisher(true)
      )(Keep.both)
      .run()

  val input = Source.fromPublisher(publisher)

  val cancel = Cancelable({ () =>
    stopper.success(None)
  })


}

object VoiceHid extends StrictLogging {

  val PacketSize = 3
  val FirstByteConstantValue = 4

  val AxisCenter = 1
  val AxisNegative = 2
  val AxisPositive = 0

  val PosVerticalAxis = 0
  val PosHorizontalAxis = 2

  val PosA = 8
  val PosB = 4
  val PosC = 7
  val PosD = 5
  val PosHigh = 11
  val PosLow = 10

  sealed trait ButtonEvent
  sealed trait ButtonDownEvent extends ButtonEvent
  case object ButtonA extends ButtonDownEvent
  case object ButtonB extends ButtonDownEvent
  case object ButtonC extends ButtonDownEvent
  case object ButtonD extends ButtonDownEvent
  case object ButtonHigh extends ButtonDownEvent
  case object ButtonLow extends ButtonDownEvent
  case object ButtonUp extends ButtonDownEvent
  case object ButtonDown extends ButtonDownEvent
  case object ButtonLeft extends ButtonDownEvent
  case object ButtonRight extends ButtonDownEvent
  case object ButtonUpLeft extends ButtonDownEvent
  case object ButtonDownLeft extends ButtonDownEvent
  case object ButtonUpRight extends ButtonDownEvent
  case object ButtonDownRight extends ButtonDownEvent
  case object Released extends ButtonEvent
  case object Unknown extends ButtonEvent

  val Directions = {
    val a = Array.fill[ButtonDownEvent](16)(null)

  }

  val Parser =
    Flow[ByteString]
      .statefulMapConcat({ () =>
        var buffer = ByteString.empty
        var last : ButtonEvent = Released

        { bs =>
          val (out, left) =
            (buffer ++ bs)
              .grouped(PacketSize)
              .to[Seq]
              .span(_.size == PacketSize)

          buffer = left.foldLeft(ByteString.empty)(_ ++ _)

          out
            .map({ bs3 =>
              require(bs3(0) == FirstByteConstantValue)
              val bits = ((bs(2) & 0xFF) << 8) | (bs(1) & 0xFF)
//              val str = Integer.toBinaryString(0x10000 + bits).drop(1)
//              val str = Integer.toHexString(0x10000 + bits).drop(1)
//              val horizontal = (bits >> PosHorizontalAxis) & 3
//              val vertical = (bits >> PosVerticalAxis) & 3
//              println(
//                s"0x${str}"
////                str.grouped(4).mkString(" ")
//              )

              bits match {
                case 0x0005 => Released
                case 0x0105 => ButtonA
                case 0x0015 => ButtonB
                case 0x0085 => ButtonC
                case 0x0025 => ButtonD
                case 0x0405 => ButtonLow
                case 0x0805 => ButtonHigh
                case 0x0009 => ButtonLeft
                case 0x0001 => ButtonRight
                case 0x0006 => ButtonDown
                case 0x0004 => ButtonUp
                case 0x000a => ButtonDownLeft
                case 0x0002 => ButtonDownRight
                case 0x0008 => ButtonUpLeft
                case 0x0000 => ButtonUpRight
                case _ => Unknown
              }
            })
            .filter({ e =>
              if (last == e) false
              else {
                last = e
                true
              }
            })
        }
      })

}
