package com.learn.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Use Sparking Streaming with Stats
 */

object StateWordCount {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("StateWordCount").setMaster("local[2]")

    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // For stateful, save checkpoint dir to HDFS dir in production
    ssc.checkpoint(".")

    val lines = ssc.socketTextStream("localhost", 6789)

    val res = lines.flatMap(_.split(" ")).map((_,1))

    val state = res.updateStateByKey(updateFunction _)

    state.print()

    ssc.start()
    ssc.awaitTermination()

  }

  /**
   * Use current values to updates previous
   * @param currentValues
   * @param preValues
   * @return
   */
  def updateFunction(currentValues: Seq[Int], preValues: Option[Int]): Option[Int] = {
    val current = currentValues.sum
    val pre = preValues.getOrElse(0)

    Some(current+pre)
  }
}
