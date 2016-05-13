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
./bin/kafka-avro-console-producer \
             --broker-list localhost:9092 --topic test \
             --property value.schema='{"type":"record","name":"myrecord","fields":[{"name":"f1","type":"string"}]}'
```

## View Test Data