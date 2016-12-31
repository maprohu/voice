package voice.sandbox.training

import java.awt.geom.{AffineTransform, Line2D}
import java.awt._
import java.awt.event.{KeyAdapter, KeyEvent, KeyListener}
import java.nio.{ByteBuffer, ByteOrder}
import javax.swing.{JFrame, JPanel}

import voice.storage.Syllables.Syllable
import voice.storage.{Consonants, Vowels}

import scala.swing.{Component, Container}

/**
  * Created by maprohu on 30-12-2016.
  */
object Display {

  def main(args: Array[String]): Unit = {
    import Consonants._
    import Vowels._
    show(Z, U)
  }

  class Line(
    val data : Iterable[Line2D.Double]
  ) {
    var visible = true

    def toggleVisible = {
      visible = !visible
    }

  }


  def show(
    c: Consonants.Value,
    v: Vowels.Value
  ) = {
    val s = Syllable(c, v)
    val datas =
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

    val lines =
      datas
        .map({ data =>
          new Line(
            data
              .toIterator
              .zipWithIndex
              .sliding(2)
              .map({
                case Seq((v0, t0), (v1, t1)) =>
                  new Line2D.Double(
                    t0, v0,
                    t1, v1
                  )
              })
              .toIterable
          )
        })

    println(datas.size)
    val maxLength = datas.map(_.length).max
    println(maxLength)

    import Color._
    val colors = Array(
      RED,
      BLUE,
      GREEN,
      GRAY,
      ORANGE,
      MAGENTA,
      CYAN
    )

    val plot = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)

        val g2 = g.asInstanceOf[Graphics2D]

        val at = AffineTransform.getTranslateInstance(0, getHeight / 2)
        at.scale(
          getWidth.toDouble / maxLength ,
          getHeight / 2.0 / Short.MinValue
        )

        g2.setTransform(at)




        g2.setColor(BLACK)
        g2.draw(
          new Line2D.Double(
            0, 0,
            maxLength, 0
          )
        )

        lines
          .zipWithIndex
          .filter({
            case (l, _) =>
              l.visible
          })
          .foreach({
            case (line, i) =>
              g2.setColor(colors(i % colors.length))
              line.data.foreach({ l =>
                g2.draw(l)
              })
          })

      }
    }
    plot.setPreferredSize(new Dimension(400, 150))

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
    frame.pack()
    frame.setVisible(true)

    frame
      .addKeyListener(
        new KeyAdapter {
          override def keyTyped(e: KeyEvent): Unit = {
            e.getKeyChar match {
              case n if '1' <= n && n <= '9' =>
                val i = n - '1'
                if (i < lines.length) {
                  lines(i).toggleVisible
                  plot.repaint()
                }
              case _ =>
            }

          }
        }
      )

  }

}
