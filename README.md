**Demo of Akka-Streams & Kafka**

## Set up

With (confluent Kafka)[http://www.confluent.io/developer#download]:

**Terminal #1**
```sh
$ ./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties
```

**Terminal #2**
```sh
$ ./bin/kafka-server-start ./etc/kafka/server.properties
```

**Terminal #3**
```
$ ./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties
```

## Running

```sh
$ sbt run
```

Open a browser at: http://localhost:9000/

## Send Test Data

```sh
./bin/kafka-console-producer --broker-list localhost:9092 --topic topic1 
```

This should send the text to the web client.


## Debugging

View all messages:

```sh
./bin/kafka-console-consumer --topic topic1 --zookeeper localhost:2181 --from-beginning
```