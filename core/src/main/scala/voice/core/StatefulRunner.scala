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
    state = processor.process(event)
    logger.info("new state: {}", state)
  }

}
