package exercise

import org.scalatest._

class UtilsSpec extends WordSpec with Matchers {

  "The utils object" when {

    "coinValues function" when {
      "passed an empty string" should {
        "return an empty Seq" in {
          utils.coinValues("") should have length 0
        }
      }

      "passed a string of just numbers" should {
        "return a List of integers" in {
          val results = utils.coinValues("2, 4, 6")
          results should have length 3
          results(0) should equal (2)
          results(1) should equal (4)
          results(2) should equal (6)
        }
      }

      "passed a string of numbers with invalid currency suffix" should {
        "return an empty List" in {
          utils.coinValues("1GG 2GG 4GG") should have length 0
        }
      }

      "passed valid Glaavo string values" should {
        "return a List of integers" in {
          val results = utils.coinValues("1G 2G 4G")
          results should have length 3
          results(0) should equal (1)
          results(1) should equal (2)
          results(2) should equal (4)
        }
      }
    }

    "glaavoString function" when {

      "passed an empty List" should {
        "return an empty string" in {
          utils.gaavoString(Seq()) should equal ("")
        }
      }

      "passed a single value in a List" should {
        "return a single Glaavo value string" in {
          utils.gaavoString(Seq(18)) should equal ("18G")
        }
      }

      "passed multiple values in a List" should {
        "return a single Glaavo value string" in {
          utils.gaavoString(Seq(1, 5, 18)) should equal ("1G, 5G, 18G")
        }
      }

      "passed a single integer" should {
        "return a single Glaavo value string" in {
          utils.gaavoString(5) should equal ("5G")
        }
      }
    }
  }
}
