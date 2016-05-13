package controllers

import javax.inject._

import play.api._
import play.api.libs.EventSource
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc._
import akka.NotUsed
import akka.stream.scaladsl.{Source, _}
import akka.util._
import play.api.libs.EventSource.Event

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import services.KafkaStreamer

@Singleton
class HomeController @Inject()()(implicit ec: ExecutionContext) extends Controller {

  def index = Action {
    Ok("Test")
  }

  def test = Action {
    Ok(views.html.index("Test"))
  }

  // see: http://loicdescotte.github.io/posts/play25-akka-streams/

  def numberStream = Action {
    // generate a stream of numbers as text
    val testSource: Source[String, NotUsed] = Source(1 to 10).map(_.toString)

    // transform the Source stream into a stream of events
    val result: Source[Event, NotUsed] = testSource via EventSource.flow

    Ok.chunked(result)
  }

}
