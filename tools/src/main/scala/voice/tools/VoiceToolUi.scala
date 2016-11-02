package voice.tools

//import toolbox6.ui.ast._
//
///**
//  * Created by pappmar on 14/10/2016.
//  */
//object VoiceToolUi {
//  def create(ui: UI) = {
//    new VoiceToolUi(ui).home
//  }
//}
//
//object Labels {
//  import toolbox6.macros.Macros.{valName => %%}
//  val REC = %%
//
//}
//
//class VoiceToolUi(val ui: UI) {
//
//  def initial(
//    record: () => Unit
//  ) = {
//    Button(
//      "Record",
//      click = record
//    )
//  }
//
//
//  def recordButton = {
//    Button(
//      "Record",
//      click = () => ui.displaySync(recording)
//    )
//  }
//
//  def playButton = {
//    Button(
//      "Play",
//      click = () => ui.displaySync(playing)
//    )
//  }
//
//  def home : Widget = {
//    recordButton
//  }
//
//  def recording : Widget = {
//    Column(
//      recordButton.copy(ability = Ability.Disabled),
//      playButton.copy(ability = Ability.Disabled),
//      Button(
//        "Stop Recording",
//        click = () => ui.displaySync(recorded)
//      )
//    )
//  }
//
//  def recorded : Widget = {
//    Column(
//      recordButton,
//      playButton,
//      Button(
//        "Stop",
//        ability = Ability.Disabled
//      )
//    )
//  }
//
//  def playing : Widget = {
//    Column(
//      recordButton.copy(ability = Ability.Disabled),
//      playButton.copy(ability = Ability.Disabled),
//      Button(
//        "Stop Playing",
//        click = () => ui.displaySync(recorded)
//      )
//    )
//
//  }
//
//}

