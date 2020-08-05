package com.learn.spark_streaming
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Use Spark Streaming to process local/hdfs data
 */

object FileWordCount {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local").setAppName("FileWordCount")

    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.textFileStream("/Users/stefanzhang 1/Desktop/temp/")

    val res = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)

    res.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
