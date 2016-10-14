package voice.android

import android.app.Activity
import android.os.Bundle
import android.widget.{Button, LinearLayout, TextView}
import toolbox6.ui.android.AndroidUI
import voice.tools.VoiceToolLogic
import monix.execution.Scheduler.Implicits.global
import rx.Rx

/**
  * Created by martonpapp on 10/10/16.
  */
class AndroidVoiceMain extends Activity {



  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)


    val ui = new AndroidUI(this)

    val binding = new AndroidVoiceTools

    Rx.unsafe {
      new VoiceToolLogic(ui, binding.binding)
    }

  }

  override def onDestroy(): Unit = super.onDestroy()
}
