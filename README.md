## Play Framework 2.5, Reactive Kafka & EventSource demo

This demo is a single page web site which takes a form input, sends it as a json XHR request to a simple [Scala Controller](https://github.com/mikebridge/kafka-akka-stream-play-demo/blob/master/app/controllers/HomeController.scala) in [Play Framework 2.5](https://www.playframework.com/), which then pipes it to a [Kafka](http://kafka.apache.org/) Unified Log.  A listener then receives a notification from Kafka via an event stream via [Reactive Kafka](https://github.com/akka/reactive-kafka) and sends it immediately back to the browser as an HTML5 EventSource event.

It's also possible also send text messages to the browser and listen to the event stream from the command line using the default command line producer/consumer that comes with Kafka.

This demo does not do anything fancy with topics, partitions groups---all messages go to and from all listening clients.

## Setup

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

## Launch Play

```sh
$ sbt run
```

Wait for the internet to download.

Open a browser at: [http://localhost:9000/](http://localhost:9000/)

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
