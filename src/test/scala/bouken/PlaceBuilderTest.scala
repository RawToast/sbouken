package bouken

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

import scala.util.Try

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
    "when given 'e50'" - {
      val place = PlaceBuilder.parse("e50")
      "creates an exit tile" in {
        place.tile shouldBe a[Exit]
      }
      "with score 50" in {
        place.tile shouldBe Exit(Score(50))
      }
    }
    "when given '0Swamp'" - {
      val place = PlaceBuilder.parse("""/0Swamp""")
      "creates a stair tile" in {
        place.tile shouldBe Stairs(To(0, "Swamp"))
      }
    }
  }
}

object PlaceBuilder {

  def parse(value: String): Place =
    if (value.exists(_ == '|')) EmptyTile
    else makeTile(parseTile(value))

  private def parseTile(string: String) =
    string match {
      case "." => Ground
      case ":" => Rough
      case "#" => Wall
      case "w" => Water
      case e if e.startsWith("e") && Try(e.drop(1).toInt).toOption.isDefined => Exit(Score(e.tail.toInt))
      case s if stairsCheck(s) => makeStairs(s)
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

  private def stairsCheck(string: String): Boolean =
    string.length >= 3 &&
      string.charAt(0) == '/' &&
      Try(string.charAt(1).toInt).toOption.isDefined

  private def makeStairs(string: String): Stairs = {
    val value = string.tail
    val id = value.takeWhile(x => Numbers.contains(x)).toInt
    val level = value.dropWhile(x => Numbers.contains(x))

    Stairs(To(id, level))
  }

  private val Numbers = "0123456789"

  private val EmptyTile = Place(
    visible = false,
    tile = Ground,
    state = Empty,
    tileEffect = NoEffect
  )
}
