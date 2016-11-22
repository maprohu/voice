package voice.akka

import javax.sound.sampled.{AudioSystem, LineEvent, LineListener}

import akka.actor.{Actor, ActorRef}
import akka.pattern._
import akka.stream.scaladsl.{Keep, Sink, SinkQueueWithCancel, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import voice.audio.AudioTools

import scala.collection.immutable._
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by maprohu on 05-11-2016.
  */
class MixerActor(
  config: MixerActor.Config
) extends Actor {
  import MixerActor._
  import config._
  import context.dispatcher
  implicit val materializer = ActorMaterializer()


  val line =
    AudioSystem
      .getSourceDataLine(
        AudioTools.audioFormat
      )
  val lineBuffer = Array.ofDim[Byte](BytesPerChunk)


  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    line.open()
    line.start()
    line.addLineListener(
      new LineListener {
        override def update(event: LineEvent): Unit = {
        }
      }
    )
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    line.stop()
    line.close()
    super.postStop()
  }
//  val availableFeedHighThreshold = line.getBufferSize - FeedLowByteSizeThreshold
  var availableLimit = Option.empty[Int]

  var state : State = Normal

  def startPlaying() = {
    availableLimit = None
  }

  def stopPlaying() = {
  }

  def play(
    queue: SoundQueue
  ) = {
    state match {
      case Normal =>
        state = Playing(Seq(queue))
        self ! Pull
        startPlaying()
      case p : Playing =>
        state = Playing(p.streams :+ queue)
    }

  }

  def shouldFeed = {
//    println(line.available())

    availableLimit.isEmpty ||
    line.available() >= availableLimit.get
  }

  def pull() = {
    state match {
      case Normal =>
        feedback !
          FeedbackActor.InvalidMixerPull
      case p : Playing =>
        if (shouldFeed) {
          Future
            .sequence(
              p
                .streams
                .map(_.pull().recover({case ex => None}))
            )
            .map(Feed.apply)
            .pipeTo(self)
        } else {
          context
            .system
            .scheduler
            .scheduleOnce(
              FeedWaitDuration,
              self,
              Pull
            )
        }
    }
  }

  def feed(
    f: Feed
  ) = {
    state match {
      case Normal =>
        feedback !
          FeedbackActor.InvalidMixerFeed
      case p: Playing =>
        val (pActive, data) = if (f.data.exists(_.isEmpty)) {
          val (inactives, actives) =
            f
              .data
              .zipWithIndex
              .partition(_._1.isEmpty)

          val removeIndices =
            inactives
              .map(_._2)
              .toSet

          val streams =
            p
              .streams
              .zipWithIndex
              .filterNot(i => removeIndices.contains(i._2))
              .map(_._1)

          println(streams.size)

          (
            Playing(
              streams
            ),
            actives.map(_._1.get)
          )
        } else {
          (p, f.data.map(_.get))
        }

        if (data.nonEmpty) {
          val iterators =
            data
              .map(_.iterator)

          var idx = 0
          while (idx < lineBuffer.length) {
            val v =
              iterators
                .map({ i =>
                  if (i.hasNext) i.next()
                  else 0
                })
                .sum

            val clip = if (v < -1) -1 else if (v > 1) 1 else v
            val i = (clip * AudioTools.MaxSampleValue).toInt
            lineBuffer(idx) = ((i >> 8) & 0xFF).toByte
            idx += 1
            lineBuffer(idx) = (i & 0xFF).toByte
            idx += 1
          }

          line.write(lineBuffer, 0, lineBuffer.length)
          if (availableLimit.isEmpty) {
            availableLimit = Some(line.available())
//            println(availableLimit)
          }
        }

        if (pActive.streams.isEmpty) {
          state = Normal
          stopPlaying()
        } else {
          self ! Pull
        }
    }
  }

  override def receive: Receive = {
    case p : Play =>
      play(
        p
          .sound
          .buffer(BufferCount, OverflowStrategy.backpressure)
          .toMat(
            Sink.queue()
          )(Keep.right)
          .run()
      )
    case Pull =>
      pull()
    case f : Feed =>
      feed(f)
  }
}

object MixerActor {
  case class Config(
    feedback: ActorRef
  )

  import AudioTools._

  type SoundChunk = Seq[Float]
  type SoundData = Source[SoundChunk, _]
  type SoundQueue = SinkQueueWithCancel[SoundChunk]

  val SamplesPerChunkPerChannel = 1024 * 8
  val SamplesPerChunk = SamplesPerChunkPerChannel * Channels
  val BytesPerChunk = SamplesPerChunk * BytesPerChannelPerSample
  val BufferCount = 4
  val DurationPerChunk = (SamplesPerChunkPerChannel * SecondsPerSamplePerChannel * 1000).millis
  val FeedWaitDuration = DurationPerChunk / 3
  val FeedLowByteSizeThreshold = BytesPerChunk * 3


  sealed trait State

  case object Normal extends State

  case class Playing(
    streams: Seq[SoundQueue]
  ) extends State

  case class Play(
    sound: SoundData
  )

  object Play {

    def apply(
      samples: Samples
    ) : Play = {
      Play(
        Source
          .apply(samples)
          .grouped(SamplesPerChunk)
      )
    }

  }

  case object Pull
  case class Feed(
    data: Seq[Option[SoundChunk]]
  )


}



