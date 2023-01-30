package controllers

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import play.api.libs.json.{Json, OFormat}

import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import models.Event

@Singleton
class EventController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // TODO: Separate persistence layer.
  // In memory list of events.
  private val events_store = mutable.ListBuffer[Event]()

  // Json formatter
  private implicit val ejf: OFormat[Event] = Json.format[Event]

  /**
   * Add a new event.
   *
   * Example:
   * ❯ curl -v -d '{"id": "1", "e_type":"oops", "e_ts":"2023-01-31"}' -H 'Content-Type: application/json' -X POST localhost:9000/event                                                                      ─╯
   */
  def add_events(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    // Extract event info from request
    val json_payload = request.body.asJson
    val event:Option[Event] = json_payload.flatMap(Json.fromJson[Event](_).asOpt)
    event match {
      case Some(newEvent) =>
        // TODO: Split event into transfer, persistence and business object.
        events_store.addOne(newEvent)
        Ok(f"Event Added successfully!")
      case None => BadRequest("Request mut include at least one event ")
    }
  }
}
