package voice.sandbox.training

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, GL20, OrthographicCamera}
import voice.sandbox.{GameLogic, GameRunner}

/**
  * Created by maprohu on 02-01-2017.
  */
object DisplayGL {

  def main(args: Array[String]): Unit = {
    import voice.storage.Vowels._
    show(RunTrainingVowelDisplay.data(
      A
    ))
  }

  def show(data: Seq[Vector[Array[Byte]]]) = {
    val shorts = Display.toShort(data)
    GameRunner
      .run(
        () => new DisplayLogic(shorts)
      )

  }

}

class DisplayLogic(data: Seq[Array[Short]]) extends GameLogic {

  val floats =
    data
      .map({ sa =>
        sa
          .zipWithIndex
          .flatMap({
            case (ys, xi) =>
              Seq(
                xi.toFloat,
                ys / -(Short.MinValue.toFloat)
              )

          })
      })

  val maxLength = data.map(_.length).max

  val aidCamera = new OrthographicCamera()
  val camera = new OrthographicCamera()
  val sr = new ShapeRenderer()

  var frameViewOffset = 0
  var frameViewLength = maxLength

  import Color._
  val colors = Vector(
    RED,
    BLUE,
    GREEN,
    YELLOW,
    BROWN,
    PURPLE
  )

  override def render: Unit = {
    Gdx.gl.glClearColor( 0, 0, 0, 1 )
    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT )

    sr.setColor(Color.WHITE)
    sr.setProjectionMatrix(aidCamera.combined)
    sr.begin(ShapeType.Line)
    sr.line(
      -1f, 0f,
      1f, 0
    )
    sr.end()


    sr.setProjectionMatrix(camera.combined)
    sr.begin(ShapeType.Line)
    floats
      .zipWithIndex
      .foreach({
        case (fs, idx) =>
//          sr.setColor(colors(idx % colors.length))
          sr.polyline(fs)
      })
    sr.end()



  }
}
