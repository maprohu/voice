package voice.core

import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.channels.{ClosedByInterruptException, FileChannel, ReadableByteChannel}

import com.typesafe.scalalogging.LazyLogging
import monix.execution.Cancelable
import toolbox6.logging.LogTools
import voice.core.events.ControllerEvent



/**
  * Created by pappmar on 21/11/2016.
  */
object HidPhysicalThread extends LazyLogging with LogTools {

  trait Processor {
    def onNext(e: ControllerEvent) : Unit
    def onError(ex: Throwable) : Unit
    def onComplete() : Unit
  }


  def run(
    input: () => (ReadableByteChannel, Processor)
  ) = {
    logger.info("starting hid physical thread")
    @volatile var running = true

    val thread = new Thread() {
      override def run(): Unit = quietly {

        logger.info("hid physical thread started")

        val buffer = Array.ofDim[Byte](3)
        val byteBuffer = ByteBuffer.wrap(buffer)

        while (running) {
          logger.info("connecting to device")

          val (is, processor) = input()

          try {
            try {
              while (running) {
                try {
                  val readCount = is.read(byteBuffer)
                  byteBuffer.flip()

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
                } catch {
                  case ex : ClosedByInterruptException =>
                    if (running) {
                      throw ex
                    } else {
                      Thread.interrupted()
                    }
                }
              }

              val ct = new Thread {
                override def run(): Unit = {
                  logger.info("completing processor")
                  processor.onComplete()
                }
              }
              ct.start()
              ct.join(600000)
              logger.info("completer thread terminated")
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

    thread.start()

    val cancel = Cancelable({ () =>
      logger.info("stopping hid physical reading")
      running = false
      thread.interrupt()
      logger.info("waiting for hid thread to stop")
      thread.join(60000)
    })

    cancel
  }

}

class InvalidPhysicalHidInputException(buffer: Array[Byte]) extends Exception(
  buffer.mkString(", ")
)
