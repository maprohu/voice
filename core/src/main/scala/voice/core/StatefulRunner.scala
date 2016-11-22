package voice.core

/**
  * Created by pappmar on 22/11/2016.
  */
class StatefulRunner[E](
  processor: StatefulProcessor[E]
) {

  var state = processor

  def process(event: E) = {
    state = processor.process(event)
  }

}
