package voice.tools

import toolbox6.ui.ast._

/**
  * Created by pappmar on 14/10/2016.
  */
object VoiceToolUi {
  def create(ui: UI) = {
    new VoiceToolUi(ui).home
  }
}

object Labels {
  import toolbox6.macros.Macros.{valName => %%}
  val REC = %%

}

class VoiceToolUi(ui: UI) {

  def recordButton = {
    Button(
      "Record",
      click = () => ui.display(recording)
    )
  }

  def home : Widget = {
    recordButton
  }

  def recording : Widget = {
    Button(
      "Stop Recording",
      click = () => ui.display(recorded)
    )
  }

  def recorded : Widget = {
    Column(
      recordButton,
      Button(
        "Play",
        click = () => ui.display(playing)
      )
    )
  }

  def playing : Widget = {
    Button(
      "Stop Playing",
      click = () => ui.display(recorded)
    )

  }

}

