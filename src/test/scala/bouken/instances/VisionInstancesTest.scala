package bouken.instances

import bouken.domain._
import bouken.types.Sight
import org.scalatest.{FreeSpec, Matchers}

class VisionInstancesTest extends FreeSpec with Matchers {

  import VisionInstancesTest._
  import bouken.syntax.vision._
  import bouken.instances.VisionInstances._


  "Visibility" - {
    "UpdateVisibility" - {
      implicit val enemySight: Sight[Enemy] = bouken.instances.SightInstances.EnemySight

      val updatedArea = blankArea.updateVisibility(player, Position(10, 10))

      "current occupied tile is visible range" in {
        updatedArea.value(Position(10, 10)).visible shouldBe true
      }

      "updates visibility of tiles in range" in {
        updatedArea.value(Position(14, 14)).visible shouldBe true
        updatedArea.value(Position(15, 15)).visible shouldBe true
        updatedArea.value(Position(5, 5)).visible shouldBe true
      }

      "occupied tile is visible" in {
        updatedArea.value(Position(10, 10)).visible shouldBe true
      }

      "updates visibility of tiles in outside of visible range" in {
        updatedArea.value(Position(16, 16)).visible shouldBe false
        updatedArea.value(Position(4, 4)).visible shouldBe false
      }

      "when the area has a wall" - {
        lazy val updatedAreaWithWall = Area(mapWithWall).updateVisibility(player, Position(10, 10))
        "the wall is visible" in {
          updatedAreaWithWall.value(Position(12, 10)).visible shouldBe true
        }

        "updates visibility of wall tiles outside of visible range" in {
          updatedAreaWithWall.value(Position(13, 20)).visible shouldBe false
        }

        "vision is blocked by the wall" in {
          updatedAreaWithWall.value(Position(13, 10)).visible shouldBe false
        }
      }

      "when the area contains tiles with vision penalties" - {
        lazy val updatedArea = Area(roughAreaMap).updateVisibility(player, Position(10, 10))
        "visible range is reduced" in {
          updatedArea.value(Position(12, 10)).visible shouldBe true
          updatedArea.value(Position(15, 15)).visible shouldBe false
        }
      }

      "when the area contains a single wall tile infront of the player" - {
        lazy val updatedArea = Area(roughAreaMap
          .updated(Position(10, 12), Place(visible = false, Wall, Empty, NoEffect))
        ).updateVisibility(player, Position(10, 10))

        "area behind the wall is not visible" in {
          updatedArea.value(Position(10, 13)).visible shouldBe false
        }

        "area diagonally behind the wall is visible" in {
          updatedArea.value(Position(9, 13)).visible shouldBe false
          updatedArea.value(Position(11, 13)).visible shouldBe false
        }

        "if the player is close then the area diagonally behind the wall is not visible" in {
          lazy val updatedArea2 = Area(roughAreaMap
            .updated(Position(10, 12), Place(visible = false, Wall, Empty, NoEffect))
          ).updateVisibility(player, Position(10, 11))
          updatedArea2.value(Position(9, 13)).visible shouldBe false
          updatedArea2.value(Position(11, 13)).visible shouldBe false
        }
      }
    }
  }
}

object VisionInstancesTest {
  val blankMap: Map[Position, Place] = (
    for {
      x <- 0 to 20
      y <- 0 to 20
      position = Position(x, y)
      place = Place(visible = false, Ground, Empty, NoEffect)
    } yield position -> place
    ).toMap

  val mapWithWall: Map[Position, Place] = (
    for {
      x <- 0 to 20
      y <- 0 to 20
      position = Position(x, y)
      place = if (x == 12) Place(visible = false, Wall, Empty, NoEffect)
      else Place(visible = false, Ground, Empty, NoEffect)
    } yield position -> place
    ).toMap

  val roughAreaMap: Map[Position, Place] = (
    for {
      x <- 0 to 20
      y <- 0 to 20
      position = Position(x, y)
      place = Place(visible = false, Rough, Empty, NoEffect)
    } yield position -> place
    ).toMap

  val blankArea: Area = Area(blankMap)
  val player = Enemy.apply(Zombie)
}
