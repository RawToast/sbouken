package bouken.server

import com.softwaremill.quicklens._

import bouken.domain._
import bouken.server.Protocol.GameViewResponse.CurrentLevel.Tile
import org.scalatest.{FreeSpec, Matchers}

class ConvertersTest extends FreeSpec with Matchers {
  import ConvertersTest._

  "Converters" - {
    "positionToTile" - {
      "given a visible empty tile" - {
        val result = Converters.positionToTile(position, emptyPlace)

        "sets the tiles position to the given position" in {
          result.position shouldBe position
        }

        "sets no description" in {
          result.description shouldBe None
        }

        "sets the tile meta" in {
          result.meta shouldBe Tile.Meta(
            tile = Tile.TileKind.Ground,
            visibility = Tile.Meta.Visibility.Visibile(7),
            player = None,
            enemyKind = None,
            tileEffect = None
          )
        }
      }

      "given an occupied tile" - {
        val place = emptyPlace.modify(_.state).setTo(Enemy(EnemyKind.Minotaur))
        val result = Converters.positionToTile(position, place)

        "sets the tiles position to the given position" in {
          result.position shouldBe position
        }

        "sets no description" in {
          result.description shouldBe None
        }

        "sets tile meta with the correct enemy" in {
          result.meta shouldBe Tile.Meta(
            tile = Tile.TileKind.Ground,
            visibility = Tile.Meta.Visibility.Visibile(7),
            player = None,
            enemyKind = Some(EnemyKind.Minotaur),
            tileEffect = None
          )
        }
      }

      "given a tile with an effect" - {
        val place = emptyPlace.modify(_.tileEffect).setTo(bouken.domain.Gold(5))
        val result = Converters.positionToTile(position, place)

        "sets the tiles position to the given position" in {
          result.position shouldBe position
        }

        "sets no description" in {
          result.description shouldBe None
        }

        "sets tile meta with the correct effect" in {
          result.meta shouldBe Tile.Meta(
            tile = Tile.TileKind.Ground,
            visibility = Tile.Meta.Visibility.Visibile(7),
            player = None,
            enemyKind = None,

            tileEffect = Some(Tile.TileEffect.Gold)
          )
        }
      }
    }
  }
}
object ConvertersTest {
  val position = Position(1, 3)
  val emptyPlace = Place(
    visible = true,
    tile = Ground,
    state = Empty,
    tileEffect = NoEffect
  )
}
