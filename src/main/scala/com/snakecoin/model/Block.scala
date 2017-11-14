package com.snakecoin.model

object Utils {
  def sha256Hash(text: String): String = {
    String.format("%064x", new java.math.BigInteger(1, java.security.MessageDigest.getInstance("SHA-256").digest(text.getBytes("UTF-8"))))
  }

  def timestamp(): Long = {
    java.time.Instant.now().toEpochMilli
  }
}


object Block {
  def apply(index: Long, timestamp: Long, data: String, previousHash: String): Block = {
    Block(index, timestamp, data, previousHash, Utils.sha256Hash(
      s"$index$timestamp$data$previousHash"
    ))
  }

  def genesisBlock(timestamp: Long): Block = {
    Block(0, timestamp, "Genesis Block", "0")
  }

  def nextBlock(lastBlock: Block, timestamp: Long): Block = {
    val newIndex = lastBlock.index + 1
    Block(
      newIndex,
      timestamp,
      s"Hey! I'm block $newIndex",
      lastBlock.hash
    )
  }
}

case class Block(index: Long, timestamp: Long, data: String, previousHash: String, hash: String) {
  println(s"Block: #$index has been added to the blockchain")
  println(s"Hash: $hash")
}


object SampleBlockchain {
  // create the genesis block
  private val gen = Block.genesisBlock(Utils.timestamp())
  // create 20 more blocks
  (0 until 20).foldLeft(gen)((prev, _) => Block.nextBlock(prev, Utils.timestamp()))
}