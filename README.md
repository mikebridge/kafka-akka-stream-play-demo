**Demo of Akka-Streams & Kafka**

This demo is a single page web site which takes a form input, pipes it to a simple [Scala Controller](https://github.com/mikebridge/kafka-akka-stream-play-demo/blob/master/app/controllers/HomeController.scala) in [Play Framework 2.5](https://www.playframework.com/), which then pipes it to a (Kafka)[http://kafka.apache.org/] Unified Log.  A listener then receives a notification from Kafka via [Reactive Kafka](https://github.com/akka/reactive-kafka) and sends it back to the browser as an HTML5 EventSource event.

This demo does not do anything fancy with topics, partitions groups---all messages go to and from all listening clients.

## Set up

Install [Confluent Kafka](http://www.confluent.io/developer#download), then:

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

You can send a message directly to the browser

```sh
./bin/kafka-console-producer --broker-list localhost:9092 --topic topic1 
```

This should send the text to the web client.


## Debugging

View all messages:

```sh
./bin/kafka-console-consumer --topic topic1 --zookeeper localhost:2181 --from-beginning
```
