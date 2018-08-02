package shard


import shard.Protocol.{G, KV, V}

object Snapshot {
  final case class States(received: Map[String, String] = Map()) {
    def updatedAdd(kv: KV): States = copy(received + (kv.key -> kv.value))

    def updatedRemove(v: V): States = {
      if(received.contains(v.key)) {
        copy(received - v.key)
      }else{
        copy(received)
      }
    }

    def getItem(k: G): String = {
      if(received.contains(k.key)) {
        received(k.key)
      }else{
        "invalid key"
      }
    }

    def getAll(): String = {
      received.toString()
    }

    override def toString = received.toString
  }

}