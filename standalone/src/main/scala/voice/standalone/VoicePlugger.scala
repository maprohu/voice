package voice.standalone

import com.typesafe.scalalogging.LazyLogging
import org.reactivestreams.Processor
import toolbox6.jartree.api._
import toolbox6.javaapi.AsyncValue
import toolbox8.jartree.standaloneapi.{JarTreeStandaloneContext, Message, PeerInfo, Service}
import toolbox8.jartree.util.{VoidService, VoidServicePlugger}

/**
  * Created by martonpapp on 15/10/16.
  */
class VoicePlugger
  extends VoidServicePlugger
  with LazyLogging {

  logger.info("voice plugger created")

}

