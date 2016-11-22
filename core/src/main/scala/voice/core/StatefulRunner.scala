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
    logger.info("state before: {}", state)
    state = state.process(event)
    logger.info("state after: {}", state)
  }

}
