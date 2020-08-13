package com.learn.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaRecieverWordCount {

  def main(args: Array[String]): Unit = {

    if(args.length != 4) {
      System.err.println("Usage: KafkaReceiverWordCount <zkQuorum> <group> <topics> <numThreads>")
    }

    val Array(zkQuorum, group, topics, numThreads) = args
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap

    val sparkConf = new SparkConf()//.setMaster("local[2]").setAppName("KafkaRecieverWordCount")

    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Spark Streaming + Kafka:
    val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }

}
