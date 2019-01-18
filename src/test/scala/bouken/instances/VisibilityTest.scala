package bouken.instances

import bouken.{Position, Sight}
import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class VisibilityTest extends FreeSpec with Matchers {
  import VisibilityTest._

  import bouken.VisibilitySyntax._
  import bouken.instances.VisionInstances._

  "Visibility" - {
    "UpdateVisibility" - {
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
    }
  }
}
object VisibilityTest {
  val blankMap: Map[Position, Place] = (
    for {
      x <- 0 to 20
      y <- 0 to 20
      position = Position(x, y)
      place = Place(visible = false, Ground, Empty, NoEffect)
    } yield position -> place
    ).toMap

  val blankArea: Area = Area(blankMap)

  val player = Player("test")
  implicit val basicPlayerSight: Sight[Player] = new Sight[Player] {
    override def visionCost(b: Player, place: Place): Double = place.tile match {
      case Ground => 1.0
      case Rough => 1.5
      case Water => 1.0
      case Wall => 99
    }

    override val range: Double = 5
  }
}