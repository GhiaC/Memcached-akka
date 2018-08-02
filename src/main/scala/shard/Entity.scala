package shard

import akka.actor._
import akka.persistence.{PersistentActor, SaveSnapshotFailure, SaveSnapshotSuccess, SnapshotOffer}
import shard.Snapshot.States

class Entity extends PersistentActor with ActorLogging {
  //Unique persistence
  //  def persistenceId: String = "PersistenceId-" + self.path.name

  //Global persistence
  def persistenceId: String = "Persistence-Global1"

  def receiveCommand: Receive = {
    case "print" => println("current state = " + Shard.state)
    case "snap" => saveSnapshot(Shard.state)
    case SaveSnapshotSuccess(metadata) => // ...
    case SaveSnapshotFailure(metadata, reason) => // ...
    case msg: Protocol.Set =>
      persist(msg) { m =>
        Shard.state = Shard.state.updatedAdd(Protocol.KV(m.key, m.value))
      }
//      println("current state = " + Shard.state)
    case msg: Protocol.Remove =>
      persist(msg) { m =>
        Shard.state = Shard.state.updatedRemove(Protocol.V(m.key))
      }
//      println("current state = " + Shard.state)
    case msg: Protocol.GetItem =>
      println(Shard.state.getItem(Protocol.G(msg.key)))
    case msg: Protocol.GetAll =>
      println(Shard.state.getAll())
  }

  def receiveRecover: Receive = {
    case SnapshotOffer(_, s: States) =>
      println("offered state = " + s)
      Shard.state = s
    case msg: Protocol.Set =>
      Shard.state = Shard.state.updatedAdd(Protocol.KV(msg.key, msg.value))
    case msg: Protocol.Remove =>
      Shard.state = Shard.state.updatedRemove(Protocol.V(msg.key))
    case msg: Protocol.GetItem =>
      println(Shard.state.getItem(Protocol.G(msg.key)))
    case msg: Protocol.GetAll =>
      println(Shard.state.getAll())

  }
}
