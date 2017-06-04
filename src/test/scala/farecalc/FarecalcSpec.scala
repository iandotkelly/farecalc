package exercise

import org.scalatest._

class FarecalcSpec extends WordSpec with Matchers {

  "The FareCalc constructor" when {

    "passed negative total" should {
      "throw IllegalArgumentException" in {
        assertThrows[IllegalArgumentException] {
          new FareCalc(Seq(1, 2, 3), -1)
        }
      }
    }

    "passed an empty coin sequence" should {
      "throw IllegalArgumentException" in {
        assertThrows[IllegalArgumentException] {
          new FareCalc(Seq(), 42)
        }
      }
    }

    "passed any negative coin values" should {
      "throw IllegalArgumentException" in {
        assertThrows[IllegalArgumentException] {
          new FareCalc(Seq(1, -2, 3), 42)
        }
      }
    }
  }

  "The FareCalc.findCombination method" when {

    "given no coin smaller than the target" should {
      "not find a solution and return null" in {
        val calc = new FareCalc(Seq(10, 29, 30), 9)
        val result = calc.findCombination()
        result should equal(null)
      }
    }

    "Given 1G, 1G, 3G, 9G, 9G and a target of 11G" should {
      "find solution 1G, 1G, 9G" in {
        val calc = new FareCalc(Seq(1, 1, 3, 9, 9), 11)
        val result = calc.findCombination()
        result should have length 3
        result(0) should equal(1)
        result(1) should equal(1)
        result(2) should equal(9)
      }
    }

    "Given 9G, 9G, 3G, 1G, 1G and a target of 11G" should {
      "find same solution 1G, 1G, 9G" in {
        val calc = new FareCalc(Seq(9, 9, 3, 1, 1), 11)
        val result = calc.findCombination()
        result should have length 3
        result(0) should equal(1)
        result(1) should equal(1)
        result(2) should equal(9)
      }
    }

    "Given 1G, 1G, 3G, 9G, 9G and a target of 15G" should {
      "not find a solution" in {
        val calc = new FareCalc(Seq(1, 1, 3, 9, 9), 15)
        val result = calc.findCombination()
        result should equal(null)
      }
    }

    // examples from the challenge

    "Given 1G 4G 6G and a target of 7G" should {
      "find same solution 1G, 6G" in {
        val calc = new FareCalc(Seq(1, 4, 6), 7)
        val result = calc.findCombination()
        result should have length 2
        result(0) should equal(1)
        result(1) should equal(6)
      }
    }

    "Given 1G 4G 6G and a target of 8G" should {
      "not find a solution" in {
        val calc = new FareCalc(Seq(1, 4, 6), 8)
        val result = calc.findCombination()
        result should equal(null)
      }
    }

    "Given 6G 13G 13G 21G 23G 24G and a target of 57G" should {
      "find a solution of 13G, 21G, 23G" in {
        val calc = new FareCalc(Seq(6,13,13,21,23,24), 57)
        val result = calc.findCombination()
        result should not equal (null)
        result should have length 3
        result(0) should equal (13)
        result(1) should equal (21)
        result(2) should equal (23)
      }
    }
  }
}

