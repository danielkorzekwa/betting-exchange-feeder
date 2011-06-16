package dk.betex.feeder

/**
 * Parses input arguments..
 *
 * @author korzekwad
 *
 */
object UserInputParser {

  /**
   * Parses input arguments.
   *
   * @param args Array with input arguments.
   * @return Key - param name, Value - param value
   */
  def parse(args: Array[String]): Map[String, String] = {
    /**Parse input parameters.*/
    val argsMap: Map[String, String] = argsToMap(args)

    argsMap
  }

  /**
   * Map list of arguments to map,
   *
   * @param args
   * @return key - arg name, value - arg value, empty map is returned if can't parse input parameters.
   */
  private def argsToMap(args: Array[String]): Map[String, String] = {
    try {
      Map(args.map(arg => (arg.split("=")(0), arg.split("=")(1))): _*)
    } catch {
      case e: Exception => Map()
    }
  }
}