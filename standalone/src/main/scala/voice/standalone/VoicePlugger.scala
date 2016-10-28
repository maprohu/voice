package voice.standalone

import com.typesafe.scalalogging.LazyLogging
import toolbox8.jartree.util.{VoidService, VoidServicePlugger}

/**
  * Created by martonpapp on 15/10/16.
  */
class VoicePlugger
  extends VoidServicePlugger
  with LazyLogging {

  logger.info("voice plugger created")

}

