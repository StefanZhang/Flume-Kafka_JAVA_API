package com.learn.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Sparking Streaming process socket data
 *
 * Use 'nc -lk 6798' on local terminal
 */

object NetworkWordcount {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    // Steaming conext requires conf and interval.
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 6789)

    val res = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)

    res.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
