package voice.sandbox

import java.io.File
import javax.swing.table.AbstractTableModel

import scala.swing.Table.{ElementMode, IntervalMode}
import scala.swing.{Action, BorderPanel, Button, Label, MainFrame, ScrollPane, Table}

/**
  * Created by martonpapp on 05/09/16.
  */
object RunVoiceTeach {
  def dataFile(index: Int) =
    new File(s"../voice/target/samples/${index}.dat")

  val Items =
    for {
      vowel <- Sounds.Vowels
      consonant <- Sounds.Consonants
    } yield {
      Seq(
        vowel,
        consonant,
        vowel
      )
    }

  trait State {
    def apply(): State
  }


  def main(args: Array[String]): Unit = {
    new MainFrame {
      title = "Voice Teach"
      visible = true
      val table =
        new Table() {
          model = new AbstractTableModel {
            val data =
              Items
                .map({ item =>
                  item.mkString(" ")
                })

            override def getRowCount: Int = data.size

            override def getColumnCount: Int = 1

            override def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef =
              data(rowIndex)

            override def isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = false
          }
          selection.elementMode = ElementMode.Row
          selection.intervalMode = IntervalMode.Single
          selection.rows.clear()
          selection.rows += 0
        }
      val scrollPane =
        new ScrollPane(
          table
        )

      contents = new BorderPanel {
        add(
          Button("Play") {
            val index =
              table.selection.rows.head
            AudioTools.play(
              dataFile(index)
            )
            val nextSelection = (index+1) % Items.size
            table.selection.rows += nextSelection
            table.peer.scrollRectToVisible(
              table.peer.getCellRect(nextSelection, 0, true)
            )
          },
          BorderPanel.Position.North
        )
        add(
          scrollPane,
          BorderPanel.Position.Center
        )
        add(
          {
            val btn = new Button("Record")
            btn.action = new Action("Record") {
              lazy val waiting : State = new State {
                def apply() = {
                  table.enabled = false
                  btn.text = "Stop"
                  recording
                }
              }
              def recording : State = new State {
                val index =
                  table.selection.rows.head
                val stopper = AudioTools.record(
                  dataFile(index)
                )
                def apply() = {
                  stopper()
                  table.selection.rows.clear()
                  val nextSelection = (index+1) % Items.size
                  table.selection.rows += nextSelection
                  table.peer.scrollRectToVisible(
                    table.peer.getCellRect(nextSelection, 0, true)
                  )
                  btn.text = "Record"
                  table.enabled = true
                  waiting
                }
              }
              var state : State = waiting
              override def apply(): Unit = {
                state = state()
              }
            }
            btn
          },
          BorderPanel.Position.South
        )


      }

      pack()
      centerOnScreen()
    }
  }

}
