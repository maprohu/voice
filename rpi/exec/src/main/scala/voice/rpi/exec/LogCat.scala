package voice.rpi.exec

import akka.stream.scaladsl.{FileIO, Flow, Sink, Source}
import akka.util.ByteString
import toolbox8.jartree.extra.shared.ExecProtocol.Executable
import toolbox8.jartree.extra.shared.HasLogFile

/**
  * Created by maprohu on 02-11-2016.
  */
class LogCat extends Executable[HasLogFile] {
  override def flow(ctx: HasLogFile): Flow[ByteString, ByteString, _] = {

    Flow
      .fromSinkAndSource(
        Sink.ignore,
        ctx
          .logFile
          .map({ lf =>
            FileIO
              .fromPath(lf)
          })
          .getOrElse(
            Source.single(
              ByteString("<log file not provided>\n")
            )
          )
      )
  }
}
