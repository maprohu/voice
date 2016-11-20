package voice.core

import akka.persistence.PersistentActor

/**
  * Created by maprohu on 20-11-2016.
  */
class RecordNatoActor extends PersistentActor {
  override def receiveRecover: Receive = ???

  override def receiveCommand: Receive = ???

  override def persistenceId: String = getClass.getSimpleName
}

object RecordNatoActor {

}

