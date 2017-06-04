package exercise

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

/**
  * Convenient singleton collection of utils
  */
object utils {

  /**
    * Convert a string of coin values to an array of integers of
    * @param input  A string which may contain Gaavo amounts (as integers or integerG)
    * @return       An ArrayBuffer of integers
    */
  def coinValues(input : String) : ArrayBuffer[Int] = {
    val output : ArrayBuffer[Int] = ArrayBuffer()
    // use a regex group to capture all the value of Gaavo
    val pattern: Regex = "([0-9]+)G?\\b".r
    pattern.findAllIn(input).matchData foreach { m => output += m.group(1).toInt }
    output
  }

  /**
    * Convert an array of integers to a list of Gaavo currency
    * @param values A sequence of integers
    * @return       A string of currency values suffixed with G, comma separates
    */
  def gaavoString(values : Seq[Int]) : String = {
    values.map(v => v + "G" : String).mkString(", ")
  }

  /**
   * Convert a single integer to the Gaavo current reprepsentation
   * @param value  The single integer to convert
   * @return       A string represeting the currency value
   */
  def gaavoString(value : Int) : String = {
    gaavoString(Seq(value))
  }

}
