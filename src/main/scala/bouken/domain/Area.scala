package bouken.domain

import bouken._

case class Area(value: Map[Position, Place]) extends AnyVal
case class Route(value: List[Position]) extends AnyVal

object Area {
  implicit val locatePosition: LocatePosition[Area, Option[Place]] =
    (a: Area, position: Position) => a.value.get(position)

  implicit val areaPathing: Pathing[Area] = new Pathing[Area] {
    import LocatePositionSyntax._
    override def canNavigate(a: Area, from: Position, to: Position, limit: Int): Boolean = {

      def loop(currentPosition: Position, turn: Int, route: Seq[Position], canNavi: Boolean): Boolean =
        if (turn > limit) false
        else if (canNavi) true
        else if (limit - turn < Math.abs(to.x - currentPosition.x)
          || limit - turn < Math.abs(to.y - currentPosition.y)) false
        else if (isOutOfBounds(a, currentPosition)) false
        else if (isInvalidTerran(a, currentPosition)) false
        else if (turn == 1 && isInvalidMove(a, currentPosition)) false
        else if (turn != 0 && route.contains(currentPosition)) false
        else if (currentPosition == to) true
        else {
          val cRoute = if (turn == 0) route else currentPosition +: route

          loop(Position(currentPosition.x - 1, currentPosition.y + 1), turn + 1, cRoute,
            loop(Position(currentPosition.x, currentPosition.y + 1), turn + 1, cRoute,
              loop(Position(currentPosition.x + 1, currentPosition.y + 1), turn + 1, cRoute,
                loop(Position(currentPosition.x - 1, currentPosition.y), turn + 1, cRoute,
                  loop(Position(currentPosition.x, currentPosition.y), turn + 1, cRoute,
                    loop(Position(currentPosition.x + 1, currentPosition.y), turn + 1, cRoute,
                      loop(Position(currentPosition.x - 1, currentPosition.y - 1), turn + 1, cRoute,
                        loop(Position(currentPosition.x, currentPosition.y - 1), turn + 1, cRoute,
                          loop(Position(currentPosition.x + 1, currentPosition.y - 1), turn + 1, cRoute,
                            canNavi)))))))))
        }

      loop(currentPosition = from, turn = 0, route = Seq.empty, canNavi = false)
    }

    override def isOutOfBounds(a: Area, position: Position): Boolean =
      position.x < 0 || position.y < 0 || a.find(position).isEmpty

    override def isInvalidTerran(a: Area, position: Position): Boolean =
      a.find(position).forall(_.tile == Wall)

    override def isInvalidMove(a: Area, position: Position): Boolean = {
      a.find(position) match {
        case Some(place) =>
          if (!Seq(Ground, Rough, Water).contains(place.tile)) true
          else if (place.state != Empty) true
          else false
        case None => true
      }
    }
  }

  implicit val navigation: Navigation[Area] = new Navigation[Area] {
    override def suggestRoute[B: MoveCosts](a: Area, mover: B, from: Position, to: Position)(
      implicit pathing: Pathing[Area]): Route = {
      import LocatePositionSyntax._

      val costCalc: MoveCosts[B] = implicitly[MoveCosts[B]]

      val placeA = a.find(from)
      val placeB = a.find(to)

      // example calcs, nothing real just compilation check
      (placeA, placeB) match {
        case (Some(fromPlace), Some(toPlace)) =>
          if (costCalc.moveCost(mover, fromPlace) > costCalc.moveCost(mover, toPlace)) Route(List(to))
          else Route(List(from))
        case (Some(_), _) => Route(List(to))
        case (_, Some(_)) => Route(List(to))
        case _ => Route(List.empty)
      }
    }
  }
}