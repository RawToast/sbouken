package bouken.world

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class PlaceParserTest extends FreeSpec with Matchers {

  "PlaceParser" - {
    "should build a basic tile" - {

      "when given '.'" - {
        val place = PlaceParser.parse(".")
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
        val place = PlaceParser.parse("#")
        "creates a wall tile" in {
          place.tile shouldBe Wall
        }
      }

      "when given ':'" - {
        val place = PlaceParser.parse(":")
        "creates a rough tile" in {
          place.tile shouldBe Rough
        }
      }

      "when given 'w'" - {
        val place = PlaceParser.parse("w")
        "creates a water tile" in {
          place.tile shouldBe Water
        }
      }

      "when given 'e50'" - {
        val place = PlaceParser.parse("e50")
        "creates an exit tile" in {
          place.tile shouldBe a[Exit]
        }
        "with score 50" in {
          place.tile shouldBe Exit(Score(50))
        }
      }

      "when given '0Swamp'" - {
        val place = PlaceParser.parse("""/0Swamp""")
        "creates a stair tile" in {
          place.tile shouldBe Stairs(To(0, "Swamp"))
        }
      }
    }

    "should build a place with an effect" - {

      "when given '.|,'" - {
        val place = PlaceParser.parse(".|,")
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "that is empty" in {
          place.state shouldBe Empty
        }
        "is not visible" in {
          place.visible shouldBe false
        }
        "and contains a trap" in {
          place.tileEffect shouldBe a[Trap]
        }
      }

      "when given '.|;'" - {
        val place = PlaceParser.parse(".|;")
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "containing a snare" in {
          place.tileEffect shouldBe a[Snare]
        }
      }

      "when given '.|+'" - {
        val place = PlaceParser.parse(".|+")
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "containing health" in {
          place.tileEffect shouldBe a[Heal]
        }
      }

      "when given '.|g'" - {
        val place = PlaceParser.parse(".|g")
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "containing gold" in {
          place.tileEffect shouldBe a[Gold]
        }
      }
    }

    "should build a place with an occupier" - {

      "when given '.|Z'" - {
        val place = PlaceParser.parse(".|Z")
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "is not visible" in {
          place.visible shouldBe false
        }
        "has no tile effects" in {
          place.tileEffect shouldBe NoEffect
        }
        "is occupied by an Enemy" in {
          place.state shouldBe a[Enemy]
        }
        "which is a Zombie" in {
          place.state shouldBe Enemy(Zombie)
        }
      }

      "when given '.|X'" - {
        val place = PlaceParser.parse(".|X")
        "creates a tile occupied by an Enemy" in {
          place.state shouldBe a[Enemy]
        }
        "which is a Gnoll" in {
          place.state shouldBe Enemy(Gnoll)
        }
      }

      "when given '.|M'" - {
        val place = PlaceParser.parse(".|M")
        "creates a tile occupied by an Enemy" in {
          place.state shouldBe a[Enemy]
        }
        "which is a Minotaur" in {
          place.state shouldBe Enemy(Minotaur)
        }
      }
    }
  }
}
