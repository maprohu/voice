package voice.core

import com.typesafe.scalalogging.StrictLogging

/**
  * Created by pappmar on 22/11/2016.
  */
class StatefulRunner[E](
  processor: StatefulProcessor[E]
) extends StrictLogging {

  var state = processor

  def process(event: E) = {
    logger.debug("state before: {}", state)
    state = state.process(event)
    logger.debug("state after: {}", state)
  }

}
