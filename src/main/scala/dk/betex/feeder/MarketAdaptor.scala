package dk.betex.feeder

import dk.betex.api._
import dk.betex._
import java.util.Date
import IMarket._
import IBet.BetTypeEnum._
import IBet.BetStatusEnum._
import com.sun.jersey.api.client._

/**Adaptor to remote betting exchange market.
 * 
 * @author korzekwad
 */
case class MarketAdaptor(val marketId:Long, resource: WebResource) extends IMarket{

  lazy val marketName: String = throw new UnsupportedOperationException("Not implemented yet.")
  lazy val eventName: String = throw new UnsupportedOperationException("Not implemented yet.")
  lazy val numOfWinners: Int = throw new UnsupportedOperationException("Not implemented yet.")
  lazy val marketTime: Date = throw new UnsupportedOperationException("Not implemented yet.")
  lazy val runners: List[IRunner] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Places a bet on a betting exchange market.
   *
   * @param betId
   * @param userId
   * @param betSize
   * @param betPrice
   * @param betType
   * @param runnerId
   *
   * @return The bet that was placed.
   */
  def placeBet(betId: Long, userId: Long, betSize: Double, betPrice: Double, betType: BetTypeEnum, runnerId: Long, placedDate: Long): IBet = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Cancels a bet on a betting exchange market.
   *
   * @param betId Unique id of a bet to be cancelled.
   *
   * @return amount cancelled
   */
  def cancelBet(betId: Long): Double = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Cancels bets on a betting exchange market.
   *
   * @param userId
   * @param betsSize Total size of bets to be cancelled.
   * @param betPrice The price that bets are cancelled on.
   * @param betType
   * @param runnerId
   *
   * @return Amount cancelled. Zero is returned if nothing is available to cancel.
   */
  def cancelBets(userId: Long, betsSize: Double, betPrice: Double, betType: BetTypeEnum, runnerId: Long): Double = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns total unmatched volume to back and to lay at all prices for all runners in a market on a betting exchange.
   *  Prices with zero volume are not returned by this method.
   *
   * @param runnerId Unique runner id that runner prices are returned for.
   * @return
   */
  def getRunnerPrices(runnerId: Long): List[IRunnerPrice] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns best toBack/toLay prices for market runner.
   * Element 1 - best price to back, element 2 - best price to lay
   * Double.NaN is returned if price is not available.
   * @return
   */
  def getBestPrices(runnerId: Long): Tuple2[IRunnerPrice, IRunnerPrice] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns best toBack/toLay prices for market.
   *
   * @return Key - runnerId, Value - market prices (element 1 - priceToBack, element 2 - priceToLay)
   */
  def getBestPrices(): Map[Long, Tuple2[IRunnerPrice, IRunnerPrice]] = throw new UnsupportedOperationException("Not implemented yet.")

  /**Returns total traded volume for all prices on all runners in a market.*/
  def getRunnerTradedVolume(runnerId: Long): IRunnerTradedVolume = throw new UnsupportedOperationException("Not implemented yet.")

  /**Returns total traded volume for a given runner.*/
  def getTotalTradedVolume(runnerId: Long): Double = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns all bets placed by user on that market.
   *
   * @param userId
   */
  def getBets(userId: Int): List[IBet] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns all bets placed by user on that market.
   *
   * @param userId
   * @param matchedBetsOnly If true then matched bets are returned only,
   * otherwise all unmatched and matched bets for user are returned.
   */
  def getBets(userId: Int, matchedBetsOnly: Boolean): List[IBet] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Returns bet for a number o criteria.
   *
   * @param userId
   * @param betStatus
   * @param betType
   * @param betPrice
   * @param runnerId
   */
  def getBets(userId: Int, betStatus: BetStatusEnum, betType: BetTypeEnum, betPrice: Double, runnerId: Long): List[IBet] = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Register listener on those matched bets, which match filter criteria
   *
   * @param filter If true then listener is triggered for this bet.
   * @param listener
   */
  def addMatchedBetsListener(filter: (IBet) => Boolean, listener: (IBet) => Unit) = throw new UnsupportedOperationException("Not implemented yet.")

  /**
   * Register listener on those unmatched bets, which match filter criteria
   *
   * @param filter If true then listener is triggered for this bet.
   * @param listener
   */
  def addUnmatchedBetsListener(filter: (IBet) => Boolean, listener: (IBet) => Unit) = throw new UnsupportedOperationException("Not implemented yet.")

   /**
   * Register listener on cancelled bets.
   *
   * @param listener
   */
  def addCancelledBetsListener(listener: (IBet) => Unit) = throw new UnsupportedOperationException("Not implemented yet.")
  
}