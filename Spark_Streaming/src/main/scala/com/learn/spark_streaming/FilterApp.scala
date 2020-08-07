package com.learn.spark_streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FilterApp {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("FilterApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    /**
     * Construct filter list
     * (zs, true), (ls, true)
     */
    val blacklist = List("zs", "ls")
    val blackRDD = ssc.sparkContext.parallelize(blacklist).map(x=>(x, true))
    val lines = ssc.socketTextStream("localhost", 6789)

    /**
     * Input be like:
     * 20200807,zs
     * 20200807,ls
     * 20200807,ww
     */
    val log = lines.map(x=>(x.split(",")(1), x)).transform(rdd=>{
      rdd.leftOuterJoin(blackRDD)
        /**
         * leftjoin:
         * (zs: [<20200807,zs>, <true>]) => drop
         * (ls: [<20200807,ls>, <true>]) => drop
         * (ww: [<20200807,ww>, <false>]) => keep
         */
        .filter(x=> x._2._2.getOrElse(false) != true)
        .map(x=>x._2._1 ) //return (ww: [<20200807,ww>, <false>]).index(2).(1) => <20200807,ww>
    })

    log.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
