package bouken.world

import org.scalatest.{FreeSpec, Matchers}

class AreaParserTest extends FreeSpec with Matchers {

  "AreaParser" - {

    "when given a valid area string" - {

      val areaParser = AreaParser(PlaceParser)

      val areaString =
        """
          | ., ., ., ., e20
          | ., ., ., .|Z, .
          | ., w, ., /0Maze, .
          | ., ., #, ., .
          | ., ., ., ., .
        """.stripMargin

      val result = areaParser.parse(areaString)

      "creates an area with the correct number of rows" in {
        result.value.count(p => p._1.y == 0) shouldBe 5
      }

      "creates an area with the correct number of columns" in {
        result.value.count(p => p._1.x == 0) shouldBe 5
      }

    }

  }

}
