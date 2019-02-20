package bouken.domain

import cats.syntax.show._
import org.scalatest.{FreeSpec, Matchers}

class PlaceTest extends FreeSpec with Matchers {
  import PlaceTest._
  "Place" - {
    "show" - {
      "should return `O` when a player is present" in {
        val place = defaultPlace.copy(state = Player("Dave", Health(5), PlayerLevelMeta(Position(0, 0), TimeDelta(0))))
        place.show shouldBe "O"
      }

      "should return `` when not visible is present" in {
        val place = defaultPlace.copy(visible = false)
        place.show shouldBe "O"
      }

      "should return `w` for an unoccupied water tile" in {
        val place = defaultPlace.copy(tile = Water)
        place.show shouldBe "w"
      }

      "should return `#` for an unoccupied wall tile" in {
        val place = defaultPlace.copy(tile = Wall)
        place.show shouldBe "#"
      }

      "should return `:` for an unoccupied rough tile" in {
        val place = defaultPlace.copy(tile = Rough)
        place.show shouldBe "#"
      }

      "should return `,` for an unoccupied trap tile" in {
        val place = defaultPlace.copy(tileEffect = Trap(5))
        place.show shouldBe ","
      }
    }
  }
}
object PlaceTest {
  val defaultPlace = Place(
    visible = true,
    tile = Ground,
    state = Empty,
    tileEffect = NoEffect
  )
}
