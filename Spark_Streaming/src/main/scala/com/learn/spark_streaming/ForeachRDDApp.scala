package com.learn.spark_streaming

import java.sql.DriverManager

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Use Sparking Streaming word count and write result into MySQL(local)
 */

object ForeachRDDApp {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("ForeachRDDApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 6789)
    val res = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    res.print()

    //TODO... put result into MySQL, no counting required
    res.foreachRDD(rdd =>{

      rdd.foreachPartition(partitionrecords =>{
        val connection = creatConnection()
        partitionrecords.foreach(record =>{
          val sql = "insert into wordcount(word, wordcount) values('" + record._1 + "'," + record._2 + ")"
          connection.createStatement().execute(sql)
        })
        connection.close()
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }

  /**
   * Get MySQL connection
   * @return
   */
  def creatConnection() = {
    Class.forName("com.mysql.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://localhost:3306/Spark", "root", "root")
  }
}
