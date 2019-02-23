package bouken.world

import bouken.domain._
import bouken.domain.EnemyKind._
import org.scalatest.{FreeSpec, Matchers}

class PlaceParserTest extends FreeSpec with Matchers {

  "PlaceParser" - {
    "should build a basic tile" - {

      "when given '.'" - {
        val optPlace = OptionPlaceParser.parse(".")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
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
        val place = OptionPlaceParser.parse("#")
        "creates a wall tile" in {
          place.map(_.tile) shouldBe Some(Wall)
        }
      }

      "when given ':'" - {
        val place = OptionPlaceParser.parse(":")
        "creates a rough tile" in {
          place.map(_.tile) shouldBe Some(Rough)
        }
      }

      "when given 'w'" - {
        val place = OptionPlaceParser.parse("w")
        "creates a water tile" in {
          place.map(_.tile) shouldBe Some(Water)
        }
      }

      "when given 'e50'" - {
        val optPlace = OptionPlaceParser.parse("e50")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
        "creates an exit tile" in {
          place.tile shouldBe a[Exit]
        }
        "with score 50" in {
          place.tile shouldBe Exit(Score(50))
        }
      }

      "when given '0Swamp'" - {
        val optPlace = OptionPlaceParser.parse("""/0Swamp""")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
        "creates a stair tile" in {
          place.tile shouldBe Stairs(To(0, "Swamp"))
        }
      }
    }

    "should build a place with an effect" - {

      "when given '.|,'" - {
        val optPlace = OptionPlaceParser.parse(".|,")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
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
        val optPlace = OptionPlaceParser.parse(".|;")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "containing a snare" in {
          place.tileEffect shouldBe a[Snare]
        }
      }

      "when given '.|+'" - {
        val optPlace = OptionPlaceParser.parse(".|+")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
        "creates a ground tile" in {
          place.tile shouldBe Ground
        }
        "containing health" in {
          place.tileEffect shouldBe a[Heal]
        }
      }

      "when given '.|g'" - {
        val optPlace = OptionPlaceParser.parse(".|g")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
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
        val optPlace = OptionPlaceParser.parse(".|Z")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
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
        val optPlace = OptionPlaceParser.parse(".|X")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
        "creates a tile occupied by an Enemy" in {
          place.state shouldBe a[Enemy]
        }
        "which is a Gnoll" in {
          place.state shouldBe Enemy(Gnoll)
        }
      }

      "when given '.|M'" - {
        val optPlace = OptionPlaceParser.parse(".|M")
        lazy val place = optPlace.get
        "decodes successfully" in {
          optPlace shouldBe a[Some[_]]
        }
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
