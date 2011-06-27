package dk.betex.feeder

import dk.betex.eventcollector.task.EventCollectorTask.EventListener
import dk.betex.eventcollector.eventprocessor._
import dk.betex.api._
import com.sun.jersey.api.client._
import org.codehaus.jettison.json._

/**
 * Feeds markets events to a betting exchange.
 *
 * @author korzekwad
 */
case class MarketEventListener(betexUrl: String) extends EventListener {

  val userId = Integer.MAX_VALUE / 3

  /**Client and resource are thread safe objects.*/
  private val client = Client.create()
  private val resource = client.resource(betexUrl)

  def onEvents(marketId: Long, events: List[String]): Unit = {

    val marketEvents = new JSONArray()
    for (event <- events) {
      marketEvents.put(new JSONObject(event))
    }
    val marketEventsData = new JSONObject()
    marketEventsData.put("userId", userId)
    marketEventsData.put("marketEvents", marketEvents)

    resource.path("/processBetexEvents").`type`("application/json").post(classOf[String], marketEventsData.toString)

  }
}