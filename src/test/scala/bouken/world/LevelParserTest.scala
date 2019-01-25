package bouken.world

import bouken.domain.Level._
import org.scalatest.{FreeSpec, Matchers}

class LevelParserTest  extends FreeSpec with Matchers {

  "AreaParser" - {
    "when given a file containing a level in the classic structure" - {

      val parser = OptionLevelParser(AreaParser(PlaceParser))
      val level = parser.parseLevel("world", "Dungeon 1.csv")

      "Uses the filename as the level name" in {
        level.map(_.name) shouldBe Some(Name("Dungeon 1"))
      }

      "Uses the default tileset" in {
        level.map(_.tileSet) shouldBe Some(TileSet.Default)
      }

      "Parses the area map" in {
        level.exists(_.area.value.isEmpty) shouldBe false
      }
    }

    "when given a file containing a level a json format" - {

      val parser = OptionLevelParser(AreaParser(PlaceParser))
      val level = parser.parseLevel("world", "OtherLevel.json")

      "Uses the filename as the level name" in {
        level.map(_.name) shouldBe Some(Name("Swamp"))
      }

      "Uses the default tileset" in {
        level.map(_.tileSet) shouldBe Some(TileSet.Default)
      }

      "Parses the area map" in {
        level.exists(_.area.value.isEmpty) shouldBe false
      }
    }
  }
}
