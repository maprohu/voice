package voice.core

import java.io.InputStream

import com.typesafe.scalalogging.LazyLogging
import monix.execution.Cancelable
import voice.core.events.ControllerEvent



/**
  * Created by pappmar on 21/11/2016.
  */
object HidPhysicalThread extends LazyLogging {

  trait Processor {
    def onNext(e: ControllerEvent) : Unit
    def onError(ex: Throwable) : Unit
    def onComplete() : Unit
  }


  def run(
    input: () => (InputStream, Processor)
  ) = {
    @volatile var running = true

    val thread = new Thread() {
      override def run(): Unit = {
        logger.info("hid physical thread started")

        val buffer = Array.ofDim[Byte](3)

        while (running) {
          logger.info("connecting to device")

          val (is, processor) = input()

          try {
            try {
              while (running) {
                val readCount = is.read(buffer)

                if (readCount != 3) {
                  throw new InvalidPhysicalHidInputException(buffer.take(readCount))
                }

                val b0 = buffer(0)
                val b1 = buffer(1)
                val b2 = buffer(2)

                if (b0 != HidParser.FirstByteConstantValue) {
                  throw new InvalidPhysicalHidInputException(buffer)
                }

                processor.onNext(
                  HidParser.decodePhysical(b1, b2)
                )
              }

              processor.onComplete()
            } catch {
              case ex : Throwable =>
                logger.error(ex.getMessage, ex)
                processor.onError(ex)
            }
          } finally {
            is.close()
          }
        }

        logger.info("hid physical thread exiting")
      }
    }

    val cancel = Cancelable({ () =>
      logger.info("stopping hid physical reading")
      running = false
      thread.interrupt()
    })

    cancel
  }

}

class InvalidPhysicalHidInputException(buffer: Array[Byte]) extends Exception(
  buffer.mkString(", ")
)
