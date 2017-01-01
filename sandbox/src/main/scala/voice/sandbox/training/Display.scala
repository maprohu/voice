package voice.sandbox.training

import java.awt.geom.{AffineTransform, Line2D}
import java.awt._
import java.awt.event._
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
        .map({ ids =>
          ids
            .map({ id =>
              TrainingDB
                .recordingData
                .get(id)
            })
        })

    showData(datas)
  }

  def showData(byteArraysSeq: Seq[Seq[Array[Byte]]]) = {
    val datas =
      byteArraysSeq
        .flatMap({ byteArrays =>
          byteArrays
            .map({ byteArray =>
              val sb = ByteBuffer
                .wrap(
                  byteArray
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

    var str = ""

    var view0 = 0
    var view1 = maxLength

    val plot = new JPanel() {
      override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)

        val g2 = g.asInstanceOf[Graphics2D]

        val at = AffineTransform.getTranslateInstance(0, getHeight / 2)
        g2.setTransform(at)

        g2.setColor(WHITE)
        g2.draw(
          new Line2D.Double(
            0, 0,
            getWidth, 0
          )
        )

        at.scale(
          getWidth.toDouble / (view1 - view0) ,
          getHeight / 2.0 / Short.MinValue
        )
        at.translate(
          -view0,
          0
        )

        g2.setTransform(at)



        lines
          .zipWithIndex
          .filter({
            case (l, _) =>
              l.visible
          })
          .foreach({
            case (line, i) =>
              g2.setColor(colors(i % colors.length))
              line
                .data
                .drop(view0)
                .take(view1)
                .foreach({ l =>
                  g2.draw(l)
                })
          })

        g2.setTransform(new AffineTransform())

        g.setColor(LIGHT_GRAY)
        g.drawString(str, 0, getHeight)

        val x = mouseDeviceX

        g.drawLine(
          x, 0,
          x, getHeight
        )

      }

      def mouseDeviceX = {
        val componentLocation = getLocationOnScreen()
        val mouseLocation =
          MouseInfo
            .getPointerInfo
            .getLocation

        mouseLocation.x - componentLocation.x
      }

      def mouseFrame = {
        val x = mouseDeviceX

        ((view1 - view0) * x / getWidth) + view0
      }
    }
    plot.setPreferredSize(new Dimension(400, 150))
    plot.setOpaque(true)
    plot.setBackground(BLACK)



    plot.addMouseMotionListener(
      new MouseMotionAdapter {
        override def mouseMoved(e: MouseEvent): Unit = {
          plot.repaint()
        }
      }
    )


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
//    frame.setGlassPane(
//      aid
//    )
//    aid.setVisible(true)
    frame.pack()
    frame.setVisible(true)

    frame
      .addKeyListener(
        new KeyAdapter {

          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
              str = ""
            }
            plot.repaint()
          }

          override def keyTyped(e: KeyEvent): Unit = {
            val ch = e.getKeyChar

            if (32 <= ch && ch < 127) {
              str += ch

              var handled = true
              str match {
                case "vd" =>
                  view0 = 0
                  view1 = maxLength
                  plot.repaint()

                case "va" =>
                  view0 = plot.mouseFrame
                  plot.repaint()

                case "vs" =>
                  view1 = plot.mouseFrame
                  plot.repaint()



                case "0" =>
                  lines.foreach(_.visible = false)
                  plot.repaint()



                case n if "1" <= n && n <= "9" =>
                  val i = n(0) - '1'
                  if (i < lines.length) {
                    lines(i).toggleVisible
                    plot.repaint()
                  }
                case _ =>
                  handled = false
              }

              if (handled) {
                str = ""
              }

            }
            plot.repaint()
          }
        }
      )

  }

}
