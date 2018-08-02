package shard

import scala.concurrent.ExecutionContext
import scala.util.Random
import akka.actor._
import akka.cluster.sharding._
import shard.Protocol.{G, GA, KV, V}
import shard.Snapshot.States

object Shard{
  var state = States()
}

class Shard extends Actor with ActorLogging {

  private val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg@Protocol.Set(id, _, _) => (id.toString, msg)
    case msg@Protocol.Remove(id, _) => (id.toString, msg)
    case msg@Protocol.GetItem(id, _) => (id.toString, msg)
    case msg@Protocol.GetAll(id) => (id.toString, msg)
  }

  override def preStart(): Unit = {
    println("Starting")
  }

  private val numberOfShards = 100

  private val extractShardId: ShardRegion.ExtractShardId = {
    case Protocol.Set(id, _, _) => (id % numberOfShards).toString
    case Protocol.Remove(id, _) => (id % numberOfShards).toString
    case Protocol.GetItem(id, _) => (id % numberOfShards).toString
    case Protocol.GetAll(id) => (id % numberOfShards).toString
    // Needed if you want to use 'remember entities':
    case ShardRegion.StartEntity(id) => (id.toLong % numberOfShards).toString
  }

  val deviceRegion: ActorRef = ClusterSharding(context.system).start(
    typeName = "Entity",
    entityProps = Props[Entity],
    settings = ClusterShardingSettings(context.system),
    extractEntityId = extractEntityId,
    extractShardId = extractShardId)

  val random = new Random()
  val numberOfDevices = 50

  implicit val ec: ExecutionContext = context.dispatcher

  def receive = {
    case t: KV =>
      val deviceId = random.nextInt(numberOfDevices)
      val msg = Protocol.Set(deviceId, t.key, t.value)
      log.info(s"Sending $msg")
      deviceRegion ! msg
    case t: V =>
      val deviceId = random.nextInt(numberOfDevices)
      val msg = Protocol.Remove(deviceId, t.key)
      log.info(s"Sending $msg")
      deviceRegion ! msg
    case t: G =>
      val deviceId = random.nextInt(numberOfDevices)
      val msg = Protocol.GetItem(deviceId, t.key)
      log.info(s"Sending $msg")
      deviceRegion ! msg
    case t: GA =>
      val deviceId = random.nextInt(numberOfDevices)
      val msg = Protocol.GetAll(deviceId)
      log.info(s"Sending $msg")
      deviceRegion ! msg
    case t :String =>
      println(t)
  }
}
