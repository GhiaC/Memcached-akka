package shard

import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}

class Supervisor extends Actor {

  override def preStart() = {
    println("The Supervisor is ready to supervise")
  }

  override def postStop() = println("Bye Bye from the Supervisor")

  override def supervisorStrategy = OneForOneStrategy() {
    case _: RestartMeException => Restart
    case _: ResumeMeException => Resume
    case _: StopMeException => Stop
  }

  val printer = context.actorOf(Props(new Shard), "printer_actor")

  override def receive: Receive = {
    case msg =>
      printer forward msg
  }
}
