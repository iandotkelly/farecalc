package exercise

object calbiaCli {

  def main(args: Array[String]): Unit = {

    if (args.length != 2) {
      println("Usage:   run \"(list of available coins)\" (fare)")
      println("example: run \"1G 3G 5G\" 8G")
      return
    }

    // not done a lot of making this robust - normally i'd find or write some
    // command line processor.  This should be fairly robust though since
    // its just looking for currency strings in the two arguments
    val coins = utils.coinValues(args(0))
    val target = utils.coinValues(args(1))(0)

    println(s"Looking for a combinations of coins ${utils.gaavoString(coins)}" +
      s" to pay fare of ${utils.gaavoString(target)}")

    val fareCalc = new FareCalc(coins, target);
    val result = fareCalc.findCombination()

    if (result == null) {
      println("No result for fare")
    } else {
      println(s"Usable combination is ${utils.gaavoString(result)}")
    }
  }
}
