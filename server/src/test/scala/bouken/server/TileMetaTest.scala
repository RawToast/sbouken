package bouken.server

import bouken.domain._
import bouken.server.Protocol.GameViewResponse.CurrentLevel.Tile
import org.scalatest.{FreeSpec, Matchers}

class PlaceTest extends FreeSpec with Matchers {
  import TileMetaTest._
  "TileMeta" - {
    "when creating an empty tile" - {
      val place = defaultPlace.copy(
        state = Empty
      )
      val result = Tile.Meta(place)

      "player should not be present" in {
        result.occupier shouldBe None
      }
    }

    "when creating a tile containing the Player" - {
      val place = defaultPlace.copy(
        state = Player(
          "Dave",
          Health(5),
          PlayerLevelMeta(Position(0, 0), TimeDelta(0))
        )
      )
      val result = Tile.Meta(place)

      "player should be present" in {
        result.occupier shouldBe Some(Tile.Meta.Occupier.Player)
      }
    }
  }
}
object TileMetaTest {
  val defaultPlace =
    Place(visible = true, tile = Ground, state = Empty, tileEffect = NoEffect)
}
