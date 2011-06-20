package dk.betex.feeder

import dk.bettingai.marketcollector.task._
import dk.bot.betfairservice._
import dk.bettingai.marketcollector.marketservice._
import org.slf4j.LoggerFactory

/**
 * The main class for Betex Feeder application.
 *
 * @author korzekwad
 */
object BetexFeederApp extends App {

  private val log = LoggerFactory.getLogger(getClass)

  printHeader()

  /**Parse input data.*/
  try {
    val inputData: Map[String, String] = UserInputParser.parse(args)
    require(
      inputData.contains("bfUser") &&
        inputData.contains("bfPassword") &&
        inputData.contains("bfProductId") &&
        inputData.contains("collectionInterval") &&
        inputData.contains("discoveryInterval") &&
        inputData.contains("startInMinutesFrom") &&
        inputData.contains("startInMinutesTo") &&
        inputData.contains("betexUrl"), "All input arguments must be provided.")

    println("Starting Betex Feeder...");

    /**Create betfair service and login to betfair account.*/
    val betfairServiceFactoryBean = new dk.bot.betfairservice.DefaultBetFairServiceFactoryBean();
    betfairServiceFactoryBean.setUser(inputData("bfUser"))
    betfairServiceFactoryBean.setPassword(inputData("bfPassword"))
    betfairServiceFactoryBean.setProductId(inputData("bfProductId").toInt)
    val loginResponse = betfairServiceFactoryBean.login
    val betfairService: BetFairService = (betfairServiceFactoryBean.getObject.asInstanceOf[BetFairService])

    /**Create event collector task.*/
    val marketService = new MarketService(betfairService)
    val maxNumberOfWinners = if (inputData.contains("maxNumOfWinners")) Option(inputData("maxNumOfWinners").toInt) else None
    val marketEventListener = MarketEventListener(inputData("betexUrl"))
    val eventCollectorTask = new EventCollectorTask(marketService, inputData("startInMinutesFrom").toInt, inputData("startInMinutesTo").toInt, maxNumberOfWinners, inputData("discoveryInterval").toInt, marketEventListener)

    val collectionInterval = inputData("collectionInterval").toLong

    println("Betex Feeder started.");

    while (true) {
      log.info("Betex Feeder is running.")
      try {
        eventCollectorTask.execute()
      } catch {
        case e: BetFairException => log.error("Betex Feeder error.", e)
      }
      Thread.sleep(collectionInterval)
    }

  } catch {
    case e: Exception => printHelpMessage(); println("\n" + e);
  }

  private def printHeader() {
    println("")
    println("***********************************************************************************")
    println("*Betting Exchange Feeder Copyright 2011 Daniel Korzekwa(http://danmachine.com)    *")
    println("*Project homepage: http://code.google.com/p/betting-exchange-feeder/              *")
    println("*Licenced under Apache License 2.0(http://www.apache.org/licenses/LICENSE-2.0)    *")
    println("***********************************************************************************")
    println("")
  }

  private def printHelpMessage() {
    println("Usage:")
    println("betex_feeder bfUser=? bfPassword=? bfProductId=? collectionInterval=? discoveryInterval=? startInMinutesFrom=? startInMinutesTo=? betexUrl=?\n")
    println("bfUser/bfPassword/bfProductId - Betfair account that is used to collect market data using Betfair public API.")
    println("collectionInterval - Market data collection interval in ms.")
    println("discoveryInterval - New markets discovery interval in ms.")
    println("startInMinutesFrom/startInMinutesTo - Market data is collected for markets with market time between startInMinutesFrom and startInMinutesTo.")
    println("betexUrl - Betting Exchange Url that market data is feeded to.")
    println("maxNumOfWinners - Collect market data for markets with number of winners less or equal to maxNumOfWinners. Default = Intifity.")
  }
}