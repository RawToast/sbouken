package bouken.world

import bouken.domain.Level
import org.scalatest.{FreeSpec, Matchers}

class WorldParserTest extends FreeSpec with Matchers {

  "WorldParser" - {

    val worldParser = OptionWorldParser(OptionLevelParser(OptionAreaParser(OptionPlaceParser)))

    "when given a directory containing a valid set of levels" - {

      val result = worldParser.parseWorld("world")

      "creates a world" in {
        result shouldBe a[Some[_]]
      }

      "sets the starting level to `Dungeon 1`" in {
        result.map(_.current) shouldBe Some(Level.Name("Dungeon 1"))
      }

      "and that level exists" in {
        result.exists(_.levels.keySet.contains(Level.Name("Dungeon 1"))) shouldBe true
      }
    }

    "when given a directory that does not exist" - {

      val result = worldParser.parseWorld("abc123")

      "creates nothing" in {
        result shouldBe None
      }
    }
  }
}
