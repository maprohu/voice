package voice.client

import java.util.concurrent.{Executors, TimeUnit}

import ammonite.ops.Path
import com.jcraft.jsch.{JSchException, Session}
import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable
import monix.execution.cancelables.{AssignableCancelable, SerialCancelable}
import toolbox6.logging.LogTools
import toolbox6.ssh.SshTools
import toolbox6.ssh.SshTools.{ForwardTunnel, ReverseTunnel}
import voice.common.SshConnectionDetails

import scala.io.Source

/**
  * Created by maprohu on 25-11-2016.
  */
object VoiceClient extends StrictLogging with LogTools {

  def run(
    target: SshConnectionDetails,
    reversePort: Int,
    forwardPorts: Seq[Int]
  ) : Cancelable = {
    val scheduler = Executors.newSingleThreadScheduledExecutor()

    val connectionCancelable = SerialCancelable()

    def doConnect() : Unit = {
      if (!connectionCancelable.isCanceled) {

        import target._
        try {
          val session = SshTools.tunnels(
            forward =
              forwardPorts
                .map(ForwardTunnel.apply),
            reverse = Seq(reversePort)
          )(
            SshTools.ConfigImpl(
              host = address,
              sshPort = port,
              user = user,
              key = Path(key),
              hostKey = hostKey
            )
          )

          val clientAddress =
            SshTools
              .execValue(
                "echo $SSH_CLIENT",
                { channel =>
                  Source
                    .fromInputStream(channel.getInputStream)
                    .mkString
                }
              )
              .split("\\s+")(0)

          val schedule = scheduler.scheduleAtFixedRate(
            new Runnable {
              override def run(): Unit = {
                if (!session.isConnected) {
                  logger.warn(s"connection dropped: ${target.address}:${target.port}")
                  doConnect()
                }
              }
            },
            SshTools.ServerAliveInterval.length,
            SshTools.ServerAliveInterval.length,
            SshTools.ServerAliveInterval.unit
          )

          connectionCancelable := Cancelable({ () =>
            quietly { schedule.cancel(false) }
            quietly { if (session.isConnected) session.disconnect() }
          })
        } catch {
          case ex : JSchException =>
            logger.warn(s"failed to connect: ${ex.getMessage}")

            val schedule = scheduler.schedule(
              new Runnable {
                override def run(): Unit = {
                  doConnect()
                }
              },
              SshTools.ServerAliveInterval.length,
              SshTools.ServerAliveInterval.unit
            )

            connectionCancelable := Cancelable({ () =>
              quietly { schedule.cancel(false) }
            })
        }
      }

    }

    doConnect()

    Cancelable({ () =>
      logger.info("stopping voice client")
      quietly {
        scheduler.shutdown()
        scheduler.awaitTermination(10, TimeUnit.SECONDS)
      }
      quietly {
        connectionCancelable.cancel()
      }
    })


  }

}
