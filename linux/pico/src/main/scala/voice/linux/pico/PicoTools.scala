package voice.linux.pico

import java.nio.ByteBuffer

import com.sun.jna.{JnaTools, Memory, Native}
import com.sun.jna.ptr.PointerByReference
import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable
import toolbox6.logging.LogTools

/**
  * Created by pappmar on 21/12/2016.
  */
object PicoTools {
  // https://github.com/DougGore/picopi

  val PicoMemorySize = 2500000





  def run = {



  }


}

class PicoInstance extends StrictLogging with LogTools {
  import PicoTools._
  import voice.linux.jna.ttspico.TtspicoLibrary._
  val libttspico = voice.linux.jna.ttspico.TtspicoLibrary.INSTANCE
  import libttspico._

  private val errorMessageBytes = Array.ofDim[Byte](200)
  private val errorMessage = ByteBuffer.wrap(errorMessageBytes)


  def picoSuccess0(system: pico_System)(what: => Int) = {
    val ret = what
    if (ret != 0) {
      pico_getSystemStatusMessage(
        system,
        ret,
        errorMessage
      )

      val msg = Native.toString(errorMessageBytes)

      throw new Exception(msg)
    }
  }

  val picoSystem : pico_System = {
    val picoMemory = new Memory(PicoMemorySize)
    val picoSystemByReference = new PointerByReference()
    val ret = pico_initialize(
      picoMemory,
      PicoMemorySize,
      picoSystemByReference
    )

    val ps = new pico_System(picoSystemByReference.getValue)

    picoSuccess0(ps)(ret)

    ps
  }

  def picoSuccess(what: => Int) = {
    picoSuccess0(picoSystem)(what)
  }

  val picoEngine = {
    val resByRef = new PointerByReference()
    picoSuccess {
      pico_loadResource(
        picoSystem.getPointer,
        JnaTools.string("/usr/share/pico/lang/en-US_ta.bin").getPointer,
        resByRef
      )
    }
    val ta = new pico_Resource(resByRef.getValue)
    picoSuccess {
      pico_loadResource(
        picoSystem.getPointer,
        JnaTools.string("/usr/share/pico/lang/en-US_lh0_sg.bin").getPointer,
        resByRef
      )
    }
    val sg = new pico_Resource(resByRef.getValue)
    val resourceNameBytes = Array.ofDim[Byte](PICO_MAX_RESOURCE_NAME_SIZE)
    val taNameBuffer = ByteBuffer.wrap(resourceNameBytes)
    picoSuccess {
      pico_getResourceName(
        picoSystem,
        ta,
        taNameBuffer
      )
    }
    val sgNameBuffer = ByteBuffer.wrap(resourceNameBytes)
    picoSuccess {
      pico_getResourceName(
        picoSystem,
        sg,
        sgNameBuffer
      )
    }
    val sgName = Native.toString(resourceNameBytes)

    val voiceName = JnaTools.stringBuffer("PicoVoice")
    picoSuccess {
      pico_createVoiceDefinition(
        picoSystem,
        voiceName
      )
    }
    picoSuccess {
      pico_addResourceToVoiceDefinition(
        picoSystem,
        voiceName,
        taNameBuffer
      )
    }
    picoSuccess {
      pico_addResourceToVoiceDefinition(
        picoSystem,
        voiceName,
        sgNameBuffer
      )
    }

    val picoEngineByRefernce = new PointerByReference()
    picoSuccess {
      pico_newEngine(
        picoSystem,
        voiceName,
        picoEngineByRefernce
      )
    }

    new pico_Engine(picoEngineByRefernce.getValue)
  }

  val cancel = Cancelable({ () =>
    quietly {
      pico_disposeEngine(
        picoSystem,
        new PointerByReference(
          picoEngine.getPointer
        )
      )
    }
    quietly {
      val ret = pico_terminate(
        new PointerByReference(
          picoSystem.getPointer
        )
      )

      require(ret == 0)
    }
  })

  def tts(
    text: String
  ) = {
    pico_putTextUtf8(
      picoSystem

    )

  }


}
