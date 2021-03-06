package bouken.instances

import bouken.domain._
import bouken.types.MoveCosts
import org.scalatest.{FreeSpec, Matchers}

class PathingTest extends FreeSpec with Matchers {
  import bouken.syntax.pathing._
  import PathingTest._

  "Pathing" - {
    "isOutOfBounds" - {
      "is true when given a negative X Position" in {
        blankArea.isOutOfBounds(Position(-1, 0)) shouldBe true
      }

      "is true when given a negative Y Position" in {
        blankArea.isOutOfBounds(Position(0, -1)) shouldBe true
      }

      "is true when given a position outside of the area" in {
        blankArea.isOutOfBounds(Position(11, 0)) shouldBe true
      }

      "is false when given a valid position" in {
        blankArea.isOutOfBounds(Position(0, 0)) shouldBe false
      }
    }

    "isInvalidTerrain" - {
      "is true when the position is invalid" in {
        blankArea.isInvalidTerran(Position(0, 11)) shouldBe true
      }

      "is true when the position references a Wall tile" in {
        blankArea.isInvalidTerran(Position(0, 11)) shouldBe true
      }

      "is false when the position references a Ground tile" in {
        blankArea.isInvalidTerran(Position(5, 5)) shouldBe false
      }
    }

    "isInvalidMove" - {
      "is true when the position is invalid" in {
        blankArea.isInvalidMove(Position(11, 11)) shouldBe true
      }

      "is true when the position references a Wall tile" in {
        walledArea.isInvalidMove(Position(0, 11)) shouldBe true
      }

      //      "is true when the position references an Occupied tile" in {
      //        blankArea.isInvalidTerran(Position(0, 11)) shouldBe true
      //      }

      "is false when the position references an empty Ground tile" in {
        blankArea.isInvalidMove(Position(5, 5)) shouldBe false
      }
    }

    "canNavigate" - {
      "Is successful when a path exists 1" in {
        blankArea.canNavigate(Position(0, 0), Position(4, 4), 4) shouldBe true
      }

      "Is successful when a path exists 2" in {
        blankArea.canNavigate(Position(0, 0), Position(0, 4), 4) shouldBe true
      }

      "Is successful when a path exists 3" in {
        blankArea.canNavigate(Position(0, 0), Position(4, 0), 4) shouldBe true
      }

      "Is successful when a path exists 4" in {
        blankArea.canNavigate(Position(0, 0), Position(2, 4), 4) shouldBe true
      }

      "Is successful when a path exists 5" in {
        blankArea.canNavigate(Position(0, 0), Position(4, 3), 4) shouldBe true
      }

      "Is successful when a path exists 6" in {
        blankArea.canNavigate(Position(4, 4), Position(0, 0), 4) shouldBe true
      }

      "Is successful when a path exists 7" in {
        blankArea.canNavigate(Position(4, 4), Position(0, 5), 4) shouldBe true
      }

      "Is successful when a path exists 8" in {
        blankArea.canNavigate(Position(4, 4), Position(5, 2), 4) shouldBe true
      }

      "Fails when the move is out of range" in {
        blankArea.canNavigate(Position(0, 0), Position(5, 5), 4) shouldBe false
      }

      "Fails when the move is out of bounds 1" in {
        blankArea.canNavigate(Position(0, 0), Position(-1, -1), 4) shouldBe false
      }

      "Fails when the move is out of bounds 2" in {
        blankArea.canNavigate(Position(9, 9), Position(11, 11), 4) shouldBe false
      }

      "Fails when no tiles are legal" in {
        walledArea.canNavigate(Position(4, 4), Position(5, 6), 4) shouldBe false
      }

      "Fails when walls block the path" in {
        horseShoe.canNavigate(Position(0, 0), Position(2, 0), 4) shouldBe false
      }

      "Is successful when there is enough range to walk around any walls" in {
        horseShoe.canNavigate(Position(0, 0), Position(2, 0), 10) shouldBe true
      }
    }
  }

  "Find Position" - {
      "Returns a place if one exists" in {
        val result = blankArea.find(Position(0, 0))

        result shouldBe Some(Place(visible = true, Ground, Empty, NoEffect))
      }

    "Returns `None` if nothing exists" in {
      val result = blankArea.find(Position(50, 0))

      result shouldBe None
    }
  }

  "Suggest Route" - {
    "When a single route exists" - {
      val start = Position(0, 0)
      val finish = Position(0, 2)
      lazy val result = horseShoe.suggestRoute(WallWalker("Dave"), start, finish)

      "Suggests a single route" in {
        result.value.size shouldBe 2
      }

      "With the expected path" in {
        result.value shouldBe List(Position(0,1), Position(0,2))
      }
    }
    "When multiple routes exist" - {
      val start = Position(0, 3)
      val finish = Position(2, 3)
      lazy val result = horseShoe.suggestRoute(WallWalker("Dave"), start, finish)

      "Suggests a single route" in {
        result.value.isEmpty shouldBe false
      }

      "That is the fastest possible" in {
        result.value shouldBe List(Position(0,4), Position(1,5), Position(2,4), Position(2,3))
      }
    }
  }

  "RecusiveArea Suggest Route" - {
    "When a single route exists" - {
      val start = Position(0, 0)
      val finish = Position(0, 2)
      lazy val result = RecursiveArea.Navigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

      "Suggests a single route" in {
        result.value.size shouldBe 2
      }

      "With the expected path" in {
        result.value shouldBe List(Position(0,1), Position(0,2))
      }
    }
    "When multiple routes exist" - {
      val start = Position(0, 3)
      val finish = Position(2, 3)
      lazy val result = RecursiveArea.Navigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

      "Suggests a single route" in {
        result.value.isEmpty shouldBe false
      }

      "That is the fastest possible" in {
        result.value shouldBe List(Position(0,4), Position(1,5), Position(2,4), Position(2,3))
      }
    }
  }
}

object PathingTest {
  val blankMap: Map[Position, Place] = (
    for {
      x <- 0 to 10
      y <- 0 to 10
      position = Position(x, y)
      place = Place(visible = true, Ground, Empty, NoEffect)
    } yield position -> place
    ).toMap

  val blankArea: Area = Area(blankMap)

  val walledArea = Area(blankMap.mapValues(_.copy(tile = Wall)))

  val horseShoe = Area((
    for {
      x <- 0 to 2
      y <- 0 to 5
      position = Position(x, y)
      place = if (x == 1 && y != 5) Place(visible = true, Wall, Empty, NoEffect)
              else Place(visible = true, Ground, Empty, NoEffect)
    } yield position -> place
    ).toMap)

  case class WallWalker(name: String)
  object WallWalker {
    implicit val moveCost: MoveCosts[WallWalker] = (b: WallWalker, place: Place) => place.tile match {
      case Ground => 1d
      case Rough => 2d
      case Wall => 5d
      case Water => 3d
      case Stairs(_) => 1d
      case Exit(_) => 1d
    }
  }
}
