package voice.requests.compilerpi

import java.io.{InputStream, OutputStream, PrintWriter}

import org.zeroturnaround.zip.ZipUtil
import toolbox8.jartree.streamapp.{Requestable, RootContext}
import ammonite.ops._
import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.io.IOUtils
import toolbox6.logging.LogTools
import voice.requests.compilerpi.CompileRpi.Env

/**
  * Created by pappmar on 07/12/2016.
  */
object CompileRpi {
  case class Env(
    includes: Seq[String] = Seq()
  )

}
class CompileRpi extends Requestable with StrictLogging with LogTools  {

  def catchOut(out: OutputStream)(what: => Unit) : Unit = {
    try {
      what
    } catch {
      case ex : Throwable =>
        logger.warn(ex.getMessage, ex)
        val pw = new PrintWriter(out)
        ex.printStackTrace(pw)
        pw.flush()
        out.flush()
    }
  }

  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    catchOut(out) {
      val dir = tmp.dir()

      run(
        dir,
        in,
        out,
        Env()
      )
    }
  }


  def run(
    dir: Path,
    in: InputStream,
    out: OutputStream,
    env: Env
  ) = catchOut(out) {
    rm(dir)
    dir.toIO.mkdirs()

    ZipUtil.unpack(
      in,
      dir.toIO
    )

    val cmd =
      Seq(
        "gcc",
        "-c", "nativeback.c",
        "-shared"
      )
      .++(
        env
          .includes
          .map(i => s"-I${i}")
      )


    val p =
      new ProcessBuilder()
        .directory(dir.toIO)
        .redirectErrorStream(true)
        .command(
          cmd:_*
        )
        .start()

    IOUtils.copy(p.getInputStream, out)
  }

}
