package dk.betex.feeder

import org.junit._
import Assert._
import dk.betex.server._
import com.sun.jersey.api.client._

class MarketEventListenerPerfTest {

  val baseUri = "http://localhost:34654/"
  val betexServer = BetexServer(baseUri)
  val betexAdaptor = BetexAdaptor(baseUri)
  val marketEventListener = MarketEventListener(betexAdaptor)

  val client = Client.create()
  val resource = client.resource(baseUri)

  @Test
  def test {
    val betEvents = List.fill(10000)("""{"time":1234568,"eventType":"PLACE_BET",	
				"betSize":10,
				"betPrice":3,
				"betType":"LAY",
				"marketId":10,
				"runnerId":11
				} """)
    
				val events = """{"time":1234567,"eventType":"CREATE_MARKET",
				"marketId":10, 
				"marketName":"Match Odds",
				"eventName":"Man Utd vs Arsenal", 
				"numOfWinners":1, 
				"marketTime":
				"2010-04-15 14:00:00", 
				"runners": [{"runnerId":11,
				"runnerName":"Man Utd"},
				{"runnerId":12, 
				"runnerName":"Arsenal"}]
				}""" :: betEvents
      
    marketEventListener.onEvents(1, events)

    /**Check market.*/
    val markets = resource.path("/getMarkets").get(classOf[String])
    assertEquals("""{"markets":[{"marketId":10,"marketName":"Match Odds","eventName":"Man Utd vs Arsenal","numOfWinners":1,"marketTime":1271336400000,"runners":[{"runnerId":11,"runnerName":"Man Utd"},{"runnerId":12,"runnerName":"Arsenal"}]}]}""", markets)

    /**Check best prices.*/
    val bestPricesMsg = resource.path("/getBestPrices").queryParam("marketId", "10").get(classOf[String])
    assertEquals("""{"marketPrices":[{"runnerId":11,"bestToBackPrice":3,"bestToBackTotal":100000},{"runnerId":12}]}""", bestPricesMsg)
  }
  
   
  @After
  def after {
    betexServer.stop()
  }

}