package bouken.world

import bouken.Position
import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class AreaParserTest extends FreeSpec with Matchers {

  "AreaParser" - {
    "when given a valid area string" - {

      val areaParser = OptionAreaParser(OptionPlaceParser)

      val areaString =
        """
          | ., ., ., ., e20
          | ., ., ., .|Z, .
          | ., w, ., /0Maze, .
          | ., ., #, ., .
          | ., ., ., ., .
        """.stripMargin

      val optResult = areaParser.parse(areaString)
      lazy val result = optResult.get

      "parses successfully" in {
        optResult shouldBe a[Some[_]]
      }

      "creates an area with the correct number of rows" in {
        result.value.count(p => p._1.y == 0) shouldBe 5
      }

      "creates an area with the correct number of columns" in {
        result.value.count(p => p._1.x == 0) shouldBe 5
      }

      "position (4, 4) should be an exit" in {
        result.value.get(Position(4, 4)).map(_.tile) shouldBe Some(Exit(Score(20)))
      }

      "position (0, 0) should be a ground tile" in {
        result.value.get(Position(0, 0)).map(_.tile) shouldBe Some(Ground)
      }

      "position (1, 2) should be a water tile" in {
        result.value.get(Position(1, 2)).map(_.tile) shouldBe Some(Water)
      }
    }
  }
}
