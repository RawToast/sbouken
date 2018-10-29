package bouken

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

class PathingTest extends FreeSpec with Matchers {

  import PathingTest._

  "Pathing" - {
    import PathingSyntax._
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
      import LocatePositionSyntax._
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
      lazy val result = Area.AreaNavigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

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
      lazy val result = Area.AreaNavigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

      "Suggests a single route" in {
        result.value.isEmpty shouldBe false
      }

      "That is the fastest possible" in {
        result.value shouldBe List(Position(0,4), Position(1,5), Position(2,4), Position(2,3))
      }
    }
  }

  "TRSuggest Route" - {

    val TrNavigation = new Navigation[Area] {
      import bouken.LocatePositionSyntax._
      override def suggestRoute[B: MoveCosts](a: Area, mover: B, from: Position, to: Position
                                             )(implicit pathing: Pathing[Area]): Route = {
        case class CostedRoute(a: Route, cost: Double)

        val limit = 9
        val costCalc: MoveCosts[B] = implicitly[MoveCosts[B]]
        def placeCost(place: Position) = a.find(place).map(costCalc.moveCost(mover, _)).getOrElse(99d)

        def bestloop(turn: Int, positions: List[Route], bestRoute: Option[Route]): Option[Route] = {
          lazy val bestLength =
            bestRoute match {
              case Some(r) => r.value.foldLeft(0d){case (c, pos) => c + placeCost(pos)}
              case None => 99d
            }

          def newPositions(currentPosition: Position) = List(Position(currentPosition.x - 1, currentPosition.y + 1),
            Position(currentPosition.x, currentPosition.y + 1),
            Position(currentPosition.x + 1, currentPosition.y + 1),
            Position(currentPosition.x - 1, currentPosition.y),
            Position(currentPosition.x, currentPosition.y),
            Position(currentPosition.x - 1, currentPosition.y - 1),
            Position(currentPosition.x, currentPosition.y - 1),
            Position(currentPosition.x + 1, currentPosition.y - 1)
          )

          val possibleRoutes: List[Route] =
            if (positions.isEmpty) newPositions(from).map(p => Route(List(p)))
            else positions.flatMap { route =>
              val last = route.value.last
              newPositions(last)
                .filterNot(route.value.contains)
                .map(p => Route(route.value :+ p))
            }
            .filterNot { route =>
              val currentPosition = route.value.last
              pathing.isOutOfBounds(a, currentPosition) ||
                pathing.isInvalidTerran(a, currentPosition) ||
                // Invalid first move
                (turn == 1 && pathing.isInvalidMove(a, currentPosition)) ||
                // Too far
                limit - turn < Math.abs(to.x - currentPosition.x) ||
                limit - turn < Math.abs(to.y - currentPosition.y)
            }

          val (complete, incomplete) = possibleRoutes.partition(_.value.contains(to))
          val updBest = bestRoute.map{
            r =>
              complete.foldLeft(CostedRoute(r, bestLength))((ca: CostedRoute, b: Route) =>
                if (b.value.foldLeft(0d){
                  case (c, pos) => c + placeCost(pos) } < ca.cost) CostedRoute(b, b.value.foldLeft(0d){ case (c, pos) => c + placeCost(pos) } )
                else ca)
          }

          if (bestRoute.nonEmpty && complete.isEmpty &&
            !incomplete.exists(_.value.foldLeft(0d){ case (c, pos) => c + placeCost(pos) } < bestLength)) bestRoute

          else bestloop(turn + 1, incomplete, bestRoute)
        }

        bestloop(0, List.empty, None)
      }
    }


    "When a single route exists" - {
      val start = Position(0, 0)
      val finish = Position(0, 2)
      lazy val result = Area.AreaNavigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

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
      lazy val result = Area.AreaNavigation.suggestRoute(horseShoe, WallWalker("Dave"), start, finish)

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
    }
  }
}
