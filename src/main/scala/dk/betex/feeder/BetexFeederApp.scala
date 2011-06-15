package dk.betex.feeder

/**The main class for Betex Feeder application.
 * 
 * @author korzekwad
 */
object BetexFeederApp extends App{

  printHeader()
  
  println("Starting Betex Feeder...");
  
  println("Betex Feeder started\nHit enter to stop it...");
  System.in.read();
  System.exit(0);
  
    private def printHeader() {
    println("")
    println("***********************************************************************************")
    println("*Betting Exchange Feeder Copyright 2011 Daniel Korzekwa(http://danmachine.com)    *")
    println("*Project homepage: http://code.google.com/p/betting-exchange-feeder/              *")
    println("*Licenced under Apache License 2.0(http://www.apache.org/licenses/LICENSE-2.0)    *")
    println("***********************************************************************************")
    println("")
  } 
}