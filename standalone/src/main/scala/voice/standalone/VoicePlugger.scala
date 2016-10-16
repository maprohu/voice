package voice.standalone

import toolbox6.jartree.api._
import toolbox8.jartree.standaloneapi.{JarTreeStandaloneContext, Service}

/**
  * Created by martonpapp on 15/10/16.
  */
class VoicePlugger
  extends ClosableJarPlugger[VoicePlugger, JarTreeStandaloneContext]
  with Service with Closable with JarUpdatable
{
  override def close(): Unit = ()
  override def update(param: Array[Byte]): Unit = ()
}

