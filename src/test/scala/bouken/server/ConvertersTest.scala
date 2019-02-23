package bouken.server

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class ConvertersTest extends FreeSpec with Matchers {

  "Converters" - {
    "positionToTile" - {

      val position = Position(1, 3)
      val place = Place(
        visible = true,
        tile = Ground,
        state = Empty,
        tileEffect = NoEffect
      )
      val result = Converters.positionToTile(position, place)

      "sets the tiles position to the given position" in {
        result.position shouldBe position
      }

      "sets no description" in {
        result.description shouldBe None
      }

      "sets a valid ASCII value for the tile" - {
        result.meta shouldBe "."
      }
    }
  }
}
