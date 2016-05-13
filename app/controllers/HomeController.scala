package controllers

import javax.inject._

import play.api._
import play.api.libs.EventSource
import play.api.libs.json._
import play.api.mvc._
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Source, _}

import play.api.libs.EventSource.Event

import scala.concurrent.ExecutionContext

//import scala.concurrent.duration._
//import services.KafkaStreamer

import akka.kafka.ConsumerSettings
import akka.kafka.scaladsl._
import akka.kafka.scaladsl.Consumer.Control


import akka.stream.ActorMaterializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
//import org.apache.kafka.common.TopicPartition

@Singleton
class HomeController @Inject()(webJarAssets: WebJarAssets)(implicit ec: ExecutionContext) extends Controller {

  implicit val actorSystem = ActorSystem("KafkaStreamDemo")
  implicit val materializer = ActorMaterializer()


  def index = Action {
    Ok("Test")
  }

  def test = Action {
    Ok(views.html.index(webJarAssets))
  }

  // see: http://loicdescotte.github.io/posts/play25-akka-streams/

  def numberStream = Action {
    // generate a stream of numbers as text
    val testSource: Source[String, NotUsed] = Source(1 to 10).map(_.toString)

    // transform the Source stream into a stream of events
    val result: Source[Event, NotUsed] = testSource via EventSource.flow

    Ok.chunked(result)
  }

  def kafkaStream = Action {

    Ok.chunked(openKafkaStream(kafkaConsumerSettings) via EventSource.flow)

  }

  def openKafkaStream(consumerSettings: ConsumerSettings[Array[Byte], String]) =
    Consumer.plainSource(consumerSettings)
      .map(x => x.value)


  def kafkaConsumerSettings: ConsumerSettings[Array[Byte], String] = {

    ConsumerSettings(actorSystem, new ByteArrayDeserializer, new StringDeserializer,
      Set("topic1"))
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  }
}
