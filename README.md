# Flume-Kafka_JAVA_API

1. Kafka_Java_API: Construct Kafka Producer and Consumer using Java API.

    Start Zookeeper:
    zkServer.sh start

    Start Kafka:
    kafka-server-start.sh $KAFKA_HOME/config/server.properties

    Start Kafka topic:
    kafka-topics.sh --create --zookeeper hadoop:2181 --replication-factor 1 --partitions 1 --topic hello_topic

    Check Kafka topic:
    kafka-topics.sh --list --zookeeper hadoop:2181
