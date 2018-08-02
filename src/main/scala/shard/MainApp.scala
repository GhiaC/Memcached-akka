package shard

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.persistence.cassandra.EventsByTagMigration

object MainApp {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + 2551).
      withFallback(ConfigFactory.load())
    val system = ActorSystem("ShardingSystem", config)

    val migrator = EventsByTagMigration(system)
    migrator.createTables()

    val actor = system.actorOf(Props[Supervisor])

    while (true) {
      val console = scala.io.StdIn.readLine()
      val operator = console.split(" ")
      if (operator(0) == "get" && operator.length == 2) {
        actor ! Protocol.G(operator(1))
      } else if (operator(0) == "set" && operator.length == 3) {
        actor ! Protocol.KV(operator(1), operator(2))
      } else if (operator(0) == "remove" && operator.length == 2) {
        actor ! Protocol.V(operator(1))
      } else if (operator(0) == "getall" && operator.length == 1) {
        actor ! Protocol.GA()
      }
    }
  }
}

