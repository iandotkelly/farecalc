package exercise

/**
  * Class which handles the actual calculation
  *
  * @param coins    ArrayBuffer of coins available
  * @param target   Target fare
  */
class FareCalc(coins : Seq[Int], target : Int) {

  /**
   * Represents a single coin, with its selected state
   * and the remaining value in the ordered wallet
   */
  private class Coin(val value : Int) {
    var selected = false
    var remaining = 0
  }

  if (coins.isEmpty) {
    throw new IllegalArgumentException("list of coins must have at least one coin")
  }

  if (coins.exists(_ < 1)) {
    throw new IllegalArgumentException("all coin values must be 1 or more")
  }

  if (target < 1) {
    throw new IllegalArgumentException("target must be 1 or more")
  }

  // convert the array of integers to Coins - filtering out
  // unneeded coins because they are already too large and sorting
  // to smallest first
  private val wallet : Seq[Coin] = {
    coins.filter(value => value <= target).sortWith(_ < _).map(value => new Coin(value))
  }

  // utility function to calculate the remaining value in the wallet for each coin in the ordered list.
  // There may be a better idiomatic way of doing this 'constructor' work in Scala.  I didn't want these
  // variables (like remaining) to be member variables of the class
  private def calcRemaining() = {
    // iterate through the wallet from highest
    // to lowest value, calculating the remaining values
    var remaining = 0
    for (index <- (wallet.length - 1) to 0 by -1) {
      val coin = wallet(index)
      coin.remaining = remaining
      remaining += coin.value
    }
  }
  calcRemaining()

  /**
    * Recursive function to find the first solution where the coins selected will sum to the
    * expected (sub)total.  Recursively it will call itself with each coin included and
    * not-included in the solution. It will prune entire branches of the solution
    * space if the sum will go over the target, and branches where there is not enough remaining
    * value in the wallet to achieve the total to avoid being a brute force O(2^N)
    *
    * @param sum          The sum expected in the subset being analyzed
    * @param index        The start index of the subset in the coin wallet
    * @param subsetTarget The target for this subset, e.g. if the target is 18 and we
    *                     already have 1G coin, this will be 17
    *
    * @return             True  A solution was found
    *                     False A solution was not found
    */
  private def subsetSum(sum: Int, index: Int, subsetTarget: Int) : Boolean = {
    val coin = wallet(index)

    // if we add this coin, do we have a solution?
    if (coin.value == subsetTarget) {
      coin.selected = true
      return true
    }

    // are we at a leaf node and can go no further?
    if (index == wallet.length - 1) {
      return false
    }

    // before we try adding the next coin, will that take us over the target?
    val nextCoin = wallet(index + 1)
    if (coin.value + nextCoin.value <= subsetTarget) {
      coin.selected = true
      if (subsetSum(sum + coin.value, index + 1, subsetTarget - coin.value)) {
        // allow solution to bubble up
        return true
      }
    }

    // before we try skipping this coin, is there enough left in the wallet
    // for us to ever hit or exceed the target
    if (coin.remaining >= subsetTarget) {
      coin.selected = false
      if (subsetSum(sum, index + 1, subsetTarget)) {
        // allow solution to bubble up
        return true
      }
    }
    false
  }


  /**
    * Method to find the first combination
    *
    * @return {Seq[Int]}  A list of coin values that match the target,
    *                     or null of no solution
    */
  def findCombination() : Seq[Int] = {
    // if there were no coins below the target, the wallet will be empty
    if (wallet.isEmpty) {
      return null
    }

    // start the  search
    if (subsetSum(0, 0, target)) {
      // just want to return a simple array of selected coins values
      return wallet.filter(coin => coin.selected).map(coin => coin.value)
    }
    null
  }
}
