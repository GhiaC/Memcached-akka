package shard

object Protocol {

  case class Set(deviceId: Int, key: String, value: String)

  case class Remove(deviceId: Int, key: String)

  case class GetItem(deviceId: Int, key: String)

  case class GetAll(deviceId: Int)

  case class KV( key: String, value: String)
  case class V(key: String)
  case class G(key: String)
  case class GA()

}

class RestartMeException extends Exception("RESTART")

class ResumeMeException extends Exception("RESUME")

class StopMeException extends Exception("STOP")