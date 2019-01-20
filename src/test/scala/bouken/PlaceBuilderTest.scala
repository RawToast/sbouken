package bouken

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class PlaceBuilderTest extends FreeSpec with Matchers {

  "PlaceBuilder" - {
    "when given '.'" - {
      val place = PlaceBuilder.parse(".")
      "creates a ground tile" in {
        place.tile shouldBe Ground
      }
      "that is empty" in {
        place.state shouldBe Empty
      }
      "is not visible" in {
        place.visible shouldBe false
      }
      "and has no effect" in {
        place.tileEffect shouldBe NoEffect
      }
    }
    "when given '#'" - {
      val place = PlaceBuilder.parse("#")
      "creates a wall tile" in {
        place.tile shouldBe Wall
      }
    }
    "when given ':'" - {
      val place = PlaceBuilder.parse(":")
      "creates a rough tile" in {
        place.tile shouldBe Rough
      }
    }
    "when given 'w'" - {
      val place = PlaceBuilder.parse("w")
      "creates a water tile" in {
        place.tile shouldBe Water
      }
    }
  }
}

object PlaceBuilder {

  def parse(value: String): Place = {
    val splitString = value.split("|")
    splitString match {
      case Array(head) => makeTile(parseTile(head))
      case Array(tile, others) => EmptyTile
      case _ => EmptyTile
    }
  }

  private def parseTile(char: String) =
    char match {
      case "." => Ground
      case ":" => Rough
      case "#" => Wall
      case "w" => Water
      case _ => Ground
    }

  private def makeTile(
    tile: Tile = Ground,
    visible: Boolean = false,
    state: Occupier = Empty,
    tileEffect: TileEffect = NoEffect
    ) = Place(
      visible,
      tile,
      state,
      tileEffect
    )

  private val EmptyTile = Place(
    visible = false,
    tile = Ground,
    state = Empty,
    tileEffect = NoEffect
  )
}
