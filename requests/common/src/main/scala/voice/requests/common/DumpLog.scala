package voice.requests.common

import java.io.{InputStream, ObjectInputStream, ObjectOutputStream, OutputStream}

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{ConsoleAppender, OutputStreamAppender}
import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable
import org.slf4j.{Logger, LoggerFactory}
import toolbox6.logging.LogTools
import toolbox8.jartree.logging.LogbackConfigurator
import toolbox8.jartree.streamapp.{Requestable, RootContext}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

/**
  * Created by maprohu on 29-11-2016.
  */
class DumpLog extends Requestable with StrictLogging with LogTools {
  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {

    val conf = new LogbackConfigurator
    val lc = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    val ca = new OutputStreamAppender[ILoggingEvent]
    ca.setContext(lc)
//    ca.setName("dump")
    ca.setEncoder(conf.createEncoder(lc))
    val rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME)

    val promise = Promise[Unit]()

    val cancel = Cancelable({ () =>
      quietly { rootLogger.detachAppender(ca) }
      quietly { ca.stop() }
      promise.success()
      logger.info("log dump removed")
    })

    val wos = new OutputStream {
      def safe[T](what: => T) = {
        try {
          what
        } catch {
          case ex : Throwable =>
            cancel.cancel()
            throw ex
        }
      }


      override def flush(): Unit = safe { out.flush() }
      override def write(b: Array[Byte], off: Int, len: Int): Unit = safe { out.write(b, off, len) }
      override def write(b: Array[Byte]): Unit = safe { out.write(b) }
      override def write(b: Int): Unit = safe { out.write(b) }
    }
    ca.setOutputStream(wos)

    ca.start
    rootLogger.addAppender(ca)

    Await.result(
      promise.future,
      Duration.Inf
    )

    logger.info("log dump stopped")
  }
}
