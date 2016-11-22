package voice.core
import voice.core.events.ControllerEvent

/**
  * Created by pappmar on 22/11/2016.
  */
trait StatefulProcessor[E] {
  def process(event: E) : StatefulProcessor[E]
}
