package voice.requests.common

import java.io.{InputStream, ObjectOutputStream, OutputStream}

import toolbox8.jartree.streamapp.{Plugged, Requestable, RootContext}

/**
  * Created by maprohu on 29-11-2016.
  */
class QueryPlugged extends Requestable {
  override def request(ctx: RootContext, in: InputStream, out: OutputStream): Unit = {
    val dos = new ObjectOutputStream(out)
    dos.writeObject(
      ctx.holder.get.classLoaderConfig
    )
    dos.flush()
  }
}
