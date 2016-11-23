package voice.core

import java.util.concurrent.{ScheduledExecutorService, TimeUnit}
import javax.sound.sampled.AudioSystem

import com.typesafe.scalalogging.LazyLogging
import voice.core.SingleMixer.PlayableSound

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by maprohu on 19-11-2016.
  */
object NatoAlphabet extends LazyLogging {

  def load(c: Char) = {
    val ais = AudioSystem.getAudioInputStream(
      getClass.getResource(s"/nato/${c}.wav")
    )


    WaveFile.samples(ais, true)
  }

  def cache(mixer: SingleMixer) = {
    logger.info("loading nato")

    val map = ('a' to 'z')
      .map({ c =>
        logger.info(s"loading nato: ${c}")
        c -> mixer.sampled(
          load(c)
        )
      })
      .toMap

    logger.info("nato loaded")
    map
  }

  lazy val Cache = cache(SingleMixer.Global)

  def readString(
    text: String,
    fn: Char => PlayableSound = Cache
  )(implicit
    scheduler: ScheduledExecutorService,
    executionContext: ExecutionContext
  ) : Future[Unit] = {
    if (text.isEmpty) Future.successful()
    else {
      val promise = Promise[Unit]()
      val p = fn(text.head)
      p
        .play
        .foreach({ frameDelay =>
          scheduler.schedule(
            new Runnable {
              override def run(): Unit = {

                promise.completeWith(
                  readString(text.tail, fn)
                )
              }
            },
            ((frameDelay + p.frames) * p.millisPerFrame).toLong,
            TimeUnit.MILLISECONDS
          )
        })
      promise.future
    }

  }

}
