package voice.tools

import toolbox6.ui.swing.SwingUI

/**
  * Created by pappmar on 14/10/2016.
  */
object RunVoiceToolSwing {

  def main(args: Array[String]): Unit = {
    SwingUI.fullScreen(VoiceToolUi.create)
  }

}
