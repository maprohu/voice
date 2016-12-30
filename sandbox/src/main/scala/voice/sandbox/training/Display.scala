package voice.sandbox.training

import java.awt.{Component, Frame}
import java.nio.{ByteBuffer, ByteOrder}
import javax.swing.JFrame

import voice.storage.Syllables.Syllable
import voice.storage.{Consonants, Vowels}

import scala.swing.{Component, Container}

/**
  * Created by maprohu on 30-12-2016.
  */
object Display {

  def show(
    c: Consonants.Value,
    v: Vowels.Value
  ) = {
    val s = Syllable(c, v)
    val ids =
      TrainingDB
        .recordingMeta
        .get()
        .data
        .get(s)
        .toSeq
        .flatMap({ ids =>
          ids
            .map({ id =>
              val sb = ByteBuffer
                .wrap(
                  TrainingDB
                    .recordingData
                    .get(id)
                )
                .order(ByteOrder.LITTLE_ENDIAN)
                .asShortBuffer()

              val a = Array.ofDim[Short](sb.capacity())
              sb.get(a)
              a
            })
        })

    val plot = new java.awt.Container {
      

    }

    val frame = new JFrame()
    frame.setDefaultCloseOperation(
      JFrame.EXIT_ON_CLOSE
    )
    frame.setExtendedState(
      Frame.MAXIMIZED_BOTH
    )
    frame.setContentPane(
      plot

    )
    frame.setVisible(true)


  }

}
