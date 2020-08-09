package com.learn.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object Flume_push_WordCount {

  def main(args: Array[String]): Unit = {

    if(args.length !=2){
      System.err.println("Usage: FlumePushWordCount <hostname> <port>")
      System.exit(1)
    }

    val Array(hostname, port) = args
    val sparkConf = new SparkConf()//.setAppName("FilterApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Get stream from 41414 based on the flume conf
    val flumeStream = FlumeUtils.createStream(ssc, hostname, port.toInt)

    flumeStream.map(x=> new String(x.event.getBody.array()).trim)
      .flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
