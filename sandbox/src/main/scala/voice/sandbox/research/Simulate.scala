package voice.sandbox.research

import com.badlogic.gdx.graphics.Color
import DisplayJogl._

/**
  * Created by pappmar on 04/01/2017.
  */
object Simulate {

  case class Spring(
    baseStiffness: Double,
    audioStiffness: Double,
    damping: Double,
    mass: Double,
    color: Color
  )

  case class SpringState(
    time: Double,
    position: Double,
    velocity: Double
  )

  def simulate(
    spring: Spring
  )(implicit
    source: Source
  ) : RenderedLine = {
    source
      .rendered
      .scanLeft(
        SpringState(source.from, 0, 0)
      )({ (state, audio) =>
        val (time, audioPosition) = audio

        val baseForce = - state.position * spring.baseStiffness
        val audioForce = (audioPosition - state.position) * spring.audioStiffness
        val dampingForce = - state.velocity * spring.damping
        val timeDifference = time - state.time

        val totalForce = baseForce + audioForce + dampingForce
        val acceleration = totalForce / spring.mass

        val velocityDifference = timeDifference * acceleration
        val positionDifference = timeDifference * state.velocity

        SpringState(
          time = time,
          position = state.position + positionDifference,
          velocity = state.velocity + velocityDifference
        )
      })
      .map({ s =>
        (s.time, s.position)
      })
  }


  def main(args: Array[String]): Unit = {
    import Color._

    val from = 0.0
    val to = 2 * Math.PI
    val step = 10e-4

    val audio =
      sinuses(
        Sinus(1, 10),
        Sinus(20, 1)
        //        Sinus(20, 1),
        //        Sinus(400, 1)
      )

    implicit val source = Source(
      from,
      to,
      step,
      audio
    )

    val mass = 1
    val omega = 20
    val stiffness = omega * omega
    val damping = Math.sqrt( 4 * stiffness * mass )


    val springs =
      Seq(
        Spring(
          baseStiffness = 0,
          audioStiffness = stiffness,
          damping = damping,
          mass = mass,
          BLUE
        )
      )

    val simulatedSprings =
      springs
        .map({ s =>
          new LineState(
            simulate(s),
            s.color
          )
        })

    val renderedAudio =
      renderLines(
        from,
        to,
        step,
        Seq(
          Line(
            audio,
            RED
          )
        )
      )

    show(
      from,
      to,
      renderedAudio ++ simulatedSprings
    )
  }

}
