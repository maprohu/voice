package voice.client

import java.net.{InetAddress, NetworkInterface}
import java.util.concurrent.{Executors, TimeUnit}

import ammonite.ops.Path
import com.jcraft.jsch.{JSchException, Session}
import com.typesafe.scalalogging.StrictLogging
import monix.execution.Cancelable
import monix.execution.cancelables.{AssignableCancelable, SerialCancelable}
import mvnmod.builder.ModulePath
import toolbox6.logging.LogTools
import toolbox6.ssh.SshTools
import toolbox6.ssh.SshTools.{ForwardTunnel, ReverseTunnel}
import toolbox8.jartree.request.StreamAppRequest
import voice.api.updateclientinfo.ClientInfo
import voice.common.SshConnectionDetails
import voice.environment.{RpiInstances, Rpis}

import scala.io.Source

/**
  * Created by maprohu on 25-11-2016.
  */
object VoiceClient extends StrictLogging with LogTools {

  def createClientInfo(
    source: Rpis.Config,
    clientAddress: String
  ) = {
    import scala.collection.JavaConversions._
    ClientInfo(
      clientId = source.id,
      publicAddress = clientAddress,
      localAddresses =
        NetworkInterface
          .getNetworkInterfaces
          .flatMap({ ni =>
            ni.getInetAddresses
          })
          .map(_.getHostAddress)
          .toVector
          .distinct
    )
  }

  def run(
    source: Rpis.Config,
    target: SshConnectionDetails
  ) : Cancelable = {
    val scheduler = Executors.newSingleThreadScheduledExecutor()

    val connectionCancelable = SerialCancelable()



    def doConnect() : Unit = {
      def reconnect() = {
        scheduler.schedule(
          new Runnable {
            override def run(): Unit = {
              doConnect()
            }
          },
          SshTools.ServerAliveInterval.length,
          SshTools.ServerAliveInterval.unit
        )
      }

      if (!connectionCancelable.isCanceled) {

        import target._
        try {
          implicit val session = SshTools.tunnels(
            forward =
              RpiInstances
                .values
                .toSeq
                .map(Rpis.servicePortFor)
                .filterNot(_ == source.servicePort)
                .map(ForwardTunnel.apply),
            reverse = Seq(source.servicePort)
          )(
            SshTools.ConfigImpl(
              host = address,
              sshPort = port,
              user = user,
              key = Path(key),
              hostKey = hostKey
            )
          )

          val schedule = try {
            logger.info("getting own public address from central")
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

            val clientInfo = createClientInfo(
              source,
              clientAddress
            )

            logger.info(s"updating client info at central: ${clientInfo}")

            StreamAppRequest
              .request(
                ClientInfo.Update,
                clientInfo,
                Rpis.Central.tunneled
              )

            logger.info("client info updated")

            scheduler.scheduleAtFixedRate(
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
          } catch {
            case ex : Throwable =>
              logger.warn(s"failed to update client info", ex)
              reconnect()
          }

          connectionCancelable := Cancelable({ () =>
            quietly { schedule.cancel(false) }
            quietly { if (session.isConnected) session.disconnect() }
          })
        } catch {
          case ex : JSchException =>
            logger.warn(s"failed to connect: ${ex.getMessage}")

            val schedule = reconnect()

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
