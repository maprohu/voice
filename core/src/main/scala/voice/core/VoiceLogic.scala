package voice.core

import java.io._
import java.nio.ByteBuffer
import java.nio.channels.{FileChannel, ReadableByteChannel}
import java.util
import java.util.concurrent.{Executors, TimeUnit}

import com.typesafe.scalalogging.{LazyLogging, StrictLogging}
import marytts.LocalMaryInterface
import marytts.server.Mary
import marytts.util.data.audio.{AudioConverterUtils, MaryAudioUtils}
import monix.execution.Cancelable
import monix.execution.atomic.Atomic
import monix.execution.cancelables.AssignableCancelable
import org.mapdb.serializer.SerializerArrayTuple
import org.mapdb.{DB, DBMaker, Serializer}
import toolbox6.logging.LogTools
import toolbox8.leveldb.{IntSize, LevelDB}
import voice.core.ShortLongProcessor.{Click, Down, Wrap, Wrapped}
import voice.core.SingleMixer.SoundForm
import voice.core.SingleRecorder.RecorderProcessor
import voice.core.Syllables.Syllable
import voice.core.events._

import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.util.Random

/**
  * Created by pappmar on 22/11/2016.
  */
object VoiceLogic extends StrictLogging with LogTools {

  var shutdownAction: () => Unit = { () =>
    Runtime.getRuntime.exec("sudo poweroff")
  }

  def connectToDevice = {
    val deviceFile =
      HidParser.HidFilePath.toFile

    while (!deviceFile.exists()) {
      logger.info("device file not present, waiting...")
      Thread.sleep(1000)
    }

    new FileInputStream(
      deviceFile
    ).getChannel
  }

  def run(
    dbDir : File,
    deviceConnection: () => ReadableByteChannel = () => connectToDevice
  )(implicit
    executionContext: ExecutionContext
  ): Cancelable = {

//    val db = LevelDB(dbDir)

    dbDir.mkdirs()
    val db =
      DBMaker
        .fileDB(new File(dbDir, "mapdb"))
        .fileMmapEnableIfSupported()
        .transactionEnable()
        .make()

    val hidCancelable = HidPhysicalThread.run({ () =>
      logger.info("starting hid reading")

      val is = deviceConnection()

      val logic = new VoiceLogic(db)

      val p =
        new HidSingleProcessor(
          new HidLongProcessor(
            new ShortLongWrapper(
              logic.Start
            )
          )
        )

      (is, p)
    })

    Cancelable({ () =>
      logger.info("stopping hid")
      quietly { hidCancelable.cancel() }
      logger.info("stopping db")
//      quietly { db.cancelable.cancel() }
      quietly { db.close() }
      quietly {
        if (Mary.currentState() == Mary.STATE_RUNNING) {
          logger.info("stopping mary")
          Mary.shutdown()

          logger.info("looking for mary shutdown hooks")
          val ash = Class.forName("java.lang.ApplicationShutdownHooks")
          val hooksField = ash.getDeclaredField("hooks")
          hooksField.setAccessible(true)

          ash.synchronized {
            val hooks =
              hooksField
                .get(null)
                .asInstanceOf[util.IdentityHashMap[Thread, Thread]]
            import scala.collection.JavaConversions._
            hooks
              .keySet()
              .toVector
              .foreach({ th =>
                if (th.getClass.getName.startsWith(classOf[Mary].getName)) {
                  logger.info(s"removing mary shutdown hook: ${th.getClass} - ${th}")
                  hooks.remove(th)
                }
              })

          }
        } else {
          logger.info("mary not running")
        }
      }
      logger.info("voice run completed")
    })

  }


}

object Tables extends Enumeration {
  val Blobs = Table()
  val Recordings = Table()
  val TTS = Table()

  case class Table() extends Val {
    val prefix = LevelDB.intPrefix(id)
  }
}

case class Recordings(
  lookup : Map[Vector[Syllable], Vector[Long]] = Map.empty
)

class VoiceLogic(
//  db: LevelDB
  db: DB
)(implicit
  executionContext: ExecutionContext
) extends StrictLogging with LogTools {


  val blobsId =
    db
      .atomicLong(s"${Tables.Blobs.toString()}_id")
      .createOrOpen()

  val blobs =
    db
      .treeMap(Tables.Blobs.toString())
      .keySerializer(Serializer.LONG)
      .valueSerializer(Serializer.BYTE_ARRAY)
      .createOrOpen()

//  val blobs = db.longTable(
//    Tables.Blobs.prefix
//  )

//  def read[T](id: Tables.Table) : Option[T] = {
//    Option(
//      db.db.get(
//        id.prefix
//      )
//    ).map({ b =>
//      val is = new ObjectInputStream(
//        new ByteArrayInputStream(
//          b
//        )
//      )
//
//      is
//        .readObject()
//        .asInstanceOf[T]
//    })
//  }
//
//  def write[T](id: Tables.Value, value: T) : Unit = {
//    val os = new ByteArrayOutputStream()
//    val oos = new ObjectOutputStream(os)
//    oos.writeObject(value)
//    oos.close()
//    db.db.put(
//      IntSize.toArray(id.id),
//      os.toByteArray
//    )
//  }

  val OkButton = Down(ButtonB)
  val CancelButton = Down(ButtonA)
  val RepeatButton = Down(ButtonC)

  val RecordingTimeoutDuration = 5.seconds

  val mixer = SingleMixer()
  val reader = new TextReader(db, mixer)
  val recorder = SingleRecorder()
  val nato = NatoAlphabet.cache(mixer)
  val ignored = mixer.render(SoundForm.sine(0.1f, 500f, 0.5f))
  val startRecording = mixer.render(SoundForm.sine(0.3f, 880f, 0.1f))
  val cancelRecording = mixer.render(SoundForm.sine(0.3f, 440f, 0.1f))
  implicit val scheduler = Executors.newSingleThreadScheduledExecutor()

  reader.read("starting voicer.")

  def shutdown() = {
    reader.readWait("stopping voicer.")

    quietly {
      logger.info("shutting down scheduler")
      scheduler.shutdown()
      scheduler.awaitTermination(5, TimeUnit.SECONDS)
      logger.info("scheduler shut down")
    }
    quietly { mixer.stop() }
    quietly { recorder.stop() }
  }

  abstract class Base extends Wrapped {
    final override def process(event: Wrap): StatefulProcessor[Wrap] = {
      event match {
        case Down(ButtonHigh) =>
          this
        case ShortLongProcessor.Long(ButtonHigh) =>
          reader.readWait("initiating system shutdown.")
          logger.info("shut down requested")
          VoiceLogic.shutdownAction()
          this

        case c: Click =>
          click(c)
        case _ =>
          stop()
          shutdown()
          Stopped
      }
    }

    def click(c: Click): Wrapped

    def stop() : Unit = ()
  }

  def ignore(w: Wrap) = {
    w match {
      case _ : Down =>
        logger.info("ignored: {}", w)
//        reader.readWait("ignored.")
        ignored.play()
      case _ =>
    }
  }

  object Stopped extends Wrapped {
    override def process(event: Wrap): StatefulProcessor[Wrap] = {
      ignore(event)
      this
    }
  }

  object Start extends Base {
    override def click(c: Click): Wrapped = c match {
      case Down(ButtonB) =>
        new Playing
      case _ =>
        ignore(c)
        this
    }
  }

  class Playing extends Base {
    val syllable = Syllables.Items(Random.nextInt(Syllables.Items.length))
    logger.info("playing: {}", syllable)


    var readStatus : Future[Unit] = null

    def doRead() = {
      readStatus = NatoAlphabet.readString(
        syllable.string,
        nato
      )
    }

    doRead()

    override def click(c: Click): Wrapped = {
      if (readStatus.isCompleted) {
        c match {
          case CancelButton =>
            new Playing
          case RepeatButton =>
            doRead()
            this

          case OkButton =>
            new Recording(syllable)
          case _ =>
            ignore(c)
            this
        }
      } else {
        ignore(c)
        this
      }
    }
  }

  class Recording(
    syllable: Syllable
  ) extends Base {
    @volatile var timeout = false

    val data = new ByteArrayOutputStream()
    val dataTarget = new RecorderProcessor {
      override def process(chunk: Array[Byte]): Unit = data.write(chunk)
    }

    @volatile var target = SingleRecorder.NullProcessor

    val stopRecording =
      recorder.record(
        new RecorderProcessor {
          override def process(chunk: Array[Byte]): Unit = target.process(chunk)
        }
      )

    val recording = AssignableCancelable.multi(
      stopRecording
    )

    val recordingRunnable =
      new Runnable {
        override def run(): Unit = {
          target = dataTarget

          val timeoutFuture = scheduler.schedule(
            new Runnable {
              override def run(): Unit = {
                logger.info(s"recording timeout after ${RecordingTimeoutDuration}")
                recording.cancel()
                timeout = true
                reader.read("time out.")
              }
            },
            RecordingTimeoutDuration.length,
            RecordingTimeoutDuration.unit
          )

          recording := Cancelable({ () =>
            timeoutFuture.cancel(false)
            stopRecording.cancel()
          })

        }
      }

    startRecording.play({ frameDelay =>
      val startFuture = scheduler.schedule(
        recordingRunnable,
//        ((frameDelay + startRecording.frames) * mixer.millisPerFrame).toLong,
        (frameDelay * mixer.millisPerFrame).toLong,
        TimeUnit.MILLISECONDS
      )

      recording := Cancelable({ () =>
        startFuture.cancel(false)
      })
    })

    override def click(c: Click): Wrapped = {
      if (timeout) {
        Start.process(c)
      } else {
        c match {
          case CancelButton =>
            recording.cancel()
//            cancelRecording.play
            reader.read("canceled.")
            Start

          case OkButton =>
            recording.cancel()
            if (timeout) {
              Start
            } else {
              new Replaying(
                syllable,
                data.toByteArray
              )
            }

          case _ =>
            ignore(c)
            this

        }

      }
    }
  }

//  var recordings = read[Recordings](Tables.Recordings).getOrElse(Recordings())
//  logger.info(s"recording db has ${recordings.lookup.size} entries with ${recordings.lookup.values.map(_.length).sum} recordings")


  val recordings =
    db
      .treeSet(Tables.Recordings.toString())
      .serializer(
        new SerializerArrayTuple(
          Serializer.SHORT_ARRAY,
          Serializer.LONG
        )
      )
      .createOrOpen()


  class Replaying(
    syllable: Syllable,
    data: Array[Byte]
  ) extends Base {
    val recorded = mixer.sampled(
      WaveFile.samples(
        data,
        1,
        false
      )
    )
    recorded.play()

    override def click(c: Click): Wrapped = c match {
      case RepeatButton =>
        recorded.play()
        this
      case OkButton =>
        val word = Vector(syllable)

        logger.info(
          s"saving: {}, ${data.length} bytes, ${data.length / recorder.config.bytesPerSample / recorder.config.samplesPerSecond} seconds",
          word
        )

        val key = Long.box(blobsId.incrementAndGet())
        blobs
          .put(
            key,
            data
          )

        recordings
          .add(
            Array(
              word.map(_.code).toArray,
              key
            )
          )
        db.commit()

//        val key = blobs.insert(data)
//        recordings = recordings.copy(
//          lookup = recordings.lookup.updated(
//            word,
//            recordings.lookup.getOrElse(word, Vector.empty[Long]) :+ key
//          )
//        )
//        write(Tables.Recordings, recordings)
        logger.info("saved with id: {}", key)
        new Playing
      case CancelButton =>
        logger.info("not saved")
//        cancelRecording.play
        reader.read("discarded.")
        Start
      case _ =>
        ignore(c)
        this

    }
  }


}

object TextGenerator extends StrictLogging {
  lazy val mary = new LocalMaryInterface

  def create(targetRate: Int) : String => Array[Byte] = { str =>
    logger.info(s"starting tts audio generation for: ${str}")
    val audio = mary.generateAudio(str)
    val resampled = if (audio.getFormat.getSampleRate.toInt == targetRate) {
      audio
    } else {
      logger.info(s"resampling from ${audio.getFormat.getSampleRate} to ${targetRate}")
      AudioConverterUtils.downSampling(audio, targetRate)
    }
    require(resampled.getFormat.getChannels == 1)
    logger.info("converting to float samples")
    val bytes = WaveFile
      .samplesBytes(resampled, false)
    logger.info(s"tts audio generated for: ${str} - size: ${bytes.length}")

    bytes
  }
}

class TextReader(
//  db: LevelDB,
  db: DB,
  mixer: SingleMixer
) extends StrictLogging {
//  val prefix = Tables.TTS.prefix
//  val table = db.sub(prefix)

  type AudioData = Array[Byte]

  val table =
    db
      .hashMap(Tables.TTS.toString())
      .keySerializer(Serializer.STRING)
      .valueSerializer(Serializer.BYTE_ARRAY)
      .createOrOpen()

  val generator = TextGenerator.create(mixer.config.audioFormat.getSampleRate.toInt)

//  def createKey(str: String) : Array[Byte] = {
//    val strBytes = str.getBytes
//    table.createKey(strBytes)
//  }


  def audioData(str: String) : AudioData = {

    val data = table.get(str)
    if (data == null) {
      val g = generator(str)
      table.put(str, g)
      db.commit()
      g
    } else {
      data
    }


//    val key = createKey(str)
//    val data = table.db.get(key)
//    if (data == null) {
//      val g = generator(str)
//      table.db.put(key, g)
//      g
//    } else {
//      data
//    }
  }

  def samples(str: String) : IndexedSeq[Float] = {
    WaveFile.samples(
      audioData(str),
      1,
      false
    )
  }

  def read(str: String)(implicit
    executionContext: ExecutionContext
  ) : Future[Unit] = {
    mixer
      .sampled(
        samples(str)
      )
      .playComplete
  }

  def readWait(str: String)(implicit
    executionContext: ExecutionContext
  ) : Unit = {
    Await.result(
      read(str),
      Duration.Inf
    )
  }
}

