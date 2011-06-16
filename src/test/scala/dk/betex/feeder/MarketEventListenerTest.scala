package dk.betex.feeder

import org.junit._
import Assert._
import dk.betex.server._

class MarketEventListenerTest {

  val baseUri = "http://localhost:34654/"
  val betexServer = BetexServer(baseUri)
  val betexAdaptor = BetexAdaptor(baseUri)
  val marketEventListener = MarketEventListener(betexAdaptor)

  @Test
  def test {
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
				}""" ::
      """{"time":1234568,"eventType":"PLACE_BET",	
				"betSize":10,
				"betPrice":3,
				"betType":"LAY",
				"marketId":10,
				"runnerId":11
				} """ ::
      """{"time":12345611,"eventType":"CANCEL_BETS","betsSize":3.0,"betPrice":3,"betType":"LAY","marketId":10,"runnerId":11}""" :: Nil

    marketEventListener.onEvents(1, events)

    val bestPrices = betexAdaptor.findMarket(10).getBestPrices()
    assertEquals("...", bestPrices)
  }

}