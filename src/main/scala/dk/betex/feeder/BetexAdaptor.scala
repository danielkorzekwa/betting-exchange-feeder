package dk.betex.feeder

import dk.betex.api._
import java.util.Date
import com.sun.jersey.api.client._

/**
 * Adaptor to remote betting exchange.
 *
 * @author korzekwad
 */
case class BetexAdaptor(betexUrl: String) extends IBetex {

  /**Client and resource are thread safe objects.*/
  private val client = Client.create()
  private val resource = client.resource(betexUrl)
  
  /**
   * Creates market on a betting exchange.
   *
   * @param market
   *
   * @return Created market
   */
  def createMarket(marketId: Long, marketName: String, eventName: String, numOfWinners: Int, marketTime: Date, runners: List[IMarket.IRunner]): IMarket = {

    val runnersString = runners.foldLeft("")((s,runner) => s + runner.runnerId + ":" + runner.runnerName  + ",").dropRight(1)
    
    val responseMsg = resource.path("/createMarket").
      queryParam("marketId", marketId.toString).
      queryParam("marketName", marketName).
      queryParam("eventName", eventName).
      queryParam("numOfWinners", numOfWinners.toString).
      queryParam("marketTime", marketTime.getTime().toString()).
      queryParam("runners", runnersString).
      get(classOf[String])

    require(responseMsg.equals("OK"), responseMsg)
    new MarketAdaptor(marketId, resource)
  }

  /**
   * Finds market for market id.
   *
   * @param marketId
   *
   * @return Found market is returned or exception is thrown if market not exists.
   */
  def findMarket(marketId: Long): IMarket = new MarketAdaptor(marketId, resource)

  /**
   * Removes market from betting exchange.
   *
   * @param marketId
   * @return Removed market or None if market didn't exist.
   */
  def removeMarket(marketId: Long): Option[IMarket] = throw new UnsupportedOperationException("Not implemented yet.")

  /**Returns all markets on a betting exchange.*/
  def getMarkets(): List[IMarket] = throw new UnsupportedOperationException("Not implemented yet.")

  /**Removes all markets and bets.*/
  def clear(): Unit = throw new UnsupportedOperationException("Not implemented yet.")
}