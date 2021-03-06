# Flume-Kafka_JAVA_API

1. Kafka_API: Construct Kafka Producer and Consumer using Java API.

    Start Zookeeper:
    zkServer.sh start

    Start Kafka:
    kafka-server-start.sh $KAFKA_HOME/config/server.properties

    Start Kafka topic:
    kafka-topics.sh --create --zookeeper hadoop:2181 --replication-factor 1 --partitions 1 --topic hello_topic

    Check Kafka topic:
    kafka-topics.sh --list --zookeeper hadoop:2181
2. Spark-Streaming: WC experiments using SCALA

    FileWordCount: WC with file system

    NetworkWordCount: WC with socket
    
    StateWordCount: WC with previous counts/states
    
    ForeachRDDApp: WC with results saved in MySQL(local)
    
    FilterApp: Filter logs that's on a given black list.
    
    Flume_push_WordCount: WC using Flume + Spark Streaming with Push method, test on both local and server.
    
    Flume_pull_WordCount: WC using Flume + Spark Streaming with Pull method, test on both local and server.

    KafkaRecieverWordCount: WC using Kafka + Spark Streaming Reciever method, test on both local and server.
