package dk.betex.feeder

import dk.betex.eventcollector.task.EventCollectorTask.EventListener
import dk.betex.eventcollector.eventprocessor._
import dk.betex.api._
import com.sun.jersey.api.client._
import org.codehaus.jettison.json._
import scala.collection.JavaConversions._
import org.slf4j.LoggerFactory

/**
 * Feeds markets events to a betting exchange.
 *
 * @author korzekwad
 */
case class MarketEventListener(betexUrl: String) extends EventListener {

  private val log = LoggerFactory.getLogger(getClass)

  private val userId = Integer.MAX_VALUE / 3

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

  override def onMarketDiscovery(marketIds: List[Long]) = {

    val markets = resource.path("/getMarkets").get(classOf[String])
    val marketsJSON = new JSONObject(markets)
    val marketsJSONArray = marketsJSON.getJSONArray("markets")
    val betexMarketIds = for (i <- 0 until marketsJSONArray.length) yield marketsJSONArray.getJSONObject(i).getLong("marketId")

    /**Remove expired markets.*/
    val marketsToBeRemoved = betexMarketIds.toList -- marketIds
    for (marketId <- marketsToBeRemoved) {
      val removeMarketStatus = resource.path("/removeMarket").queryParam("marketId", marketId.toString).get(classOf[String])
      val removeMarketStatusJSON = new JSONObject(removeMarketStatus)
      require(removeMarketStatusJSON.get("status") == "OK", "Removing market from betting exchange failed")
      log.info("Market %s has been removed from Betex".format(marketId))
    }
  }
}