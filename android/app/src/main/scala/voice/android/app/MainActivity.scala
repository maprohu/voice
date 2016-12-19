package voice.android.app

import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.{Gravity, KeyEvent, MotionEvent, ViewGroup}
import android.widget.TextView

/**
  * Created by maprohu on 18-12-2016.
  */
class MainActivity extends Activity {

  var textView : TextView = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    textView = new TextView(this)
//    textView.setMaxLines(1000)
//    textView.setVerticalScrollBarEnabled(true)
    textView.setMovementMethod(new ScrollingMovementMethod())
    textView.setGravity(Gravity.BOTTOM)

    val p = new ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT
    )
    textView.setText("booo")
    setContentView(
      textView,
      p
    )
  }

  override def dispatchGenericMotionEvent(ev: MotionEvent): Boolean = {
//    val res = super.dispatchGenericMotionEvent(ev)

    textView.append(
      s"\n${ev.toString}\n"
    )

//    res
    true
  }

  override def dispatchKeyEvent(event: KeyEvent): Boolean = {
//    val res = super.dispatchKeyEvent(event)

    textView.append(
      s"\n${event.toString}\n"
    )

//    res
    true
  }

}
