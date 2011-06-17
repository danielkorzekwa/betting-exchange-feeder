package dk.betex.feeder

import dk.bettingai.marketcollector.task.EventCollectorTask.EventListener
import dk.bettingai.marketsimulator.marketevent._
import dk.betex.api._

/**
 * Feeds markets events to a betting exchange.
 *
 * @author korzekwad
 */
case class MarketEventListener(betexAdaptor: IBetex) extends EventListener {

  val userId = Integer.MAX_VALUE / 3
  val marketEventProcessor = new MarketEventProcessorImpl(betexAdaptor)

  var lastBetId = 1

  def onEvents(marketId: Long, events: List[String]): Unit = {

    def nextBetId(): Int = {
      lastBetId += 1
      lastBetId
    }

    for (event <- events) {
      marketEventProcessor.process(event, nextBetId, userId)
    }

  }
}