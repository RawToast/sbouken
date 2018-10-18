package bouken.domain

import bouken._

case class Area(value: Map[Position, Place]) extends AnyVal
case class Route(value: List[Position]) extends AnyVal

object Area {
  implicit val LocatePosition: LocatePosition[Area, Option[Place]] =
    (a: Area, position: Position) => a.value.get(position)

  implicit val AreaPathing: Pathing[Area] = new Pathing[Area] {
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

  implicit val AreaNavigation: Navigation[Area] = new Navigation[Area] {
    override def suggestRoute[B: MoveCosts](a: Area, mover: B, from: Position, to: Position)(
      implicit pathing: Pathing[Area]): Route = {
      import LocatePositionSyntax._

      val costCalc: MoveCosts[B] = implicitly[MoveCosts[B]]
      def placeCost(place: Position) = a.find(place).map(costCalc.moveCost(mover, _)).getOrElse(99d)

      val limit = 9 // move

      def selectRoute(routeA: Route, routeB: Route, bestLength: Double) =
        if(routeA.value.foldLeft(0d){case (c, pos) => c + placeCost(pos)} < bestLength) routeA else routeB


      def loop(currentPosition: Position, turn: Int, route: List[Position], bestRoute: Route): Route = {
        lazy val bestLength =
          if (bestRoute.value.nonEmpty) bestRoute.value.foldLeft(0d){case (c, pos) => c + placeCost(pos)}
          else 99d
        lazy val history = if (turn == 0) route else route :+ currentPosition

        if (turn > limit) bestRoute
        else if (route.contains(currentPosition)) bestRoute
        else if (limit - turn < Math.abs(to.x - currentPosition.x)
          || limit - turn < Math.abs(to.y - currentPosition.y)) bestRoute
        else if (AreaPathing.isOutOfBounds(a, currentPosition)) bestRoute
        else if (AreaPathing.isInvalidTerran(a, currentPosition)) bestRoute
        else if (turn == 1 && AreaPathing.isInvalidMove(a, currentPosition)) bestRoute
        else if (currentPosition == to) selectRoute(Route(route :+ currentPosition), bestRoute, bestLength)
        else loop(Position(currentPosition.x - 1, currentPosition.y + 1), turn + 1, history,
          loop(Position(currentPosition.x, currentPosition.y + 1), turn + 1, history,
            loop(Position(currentPosition.x + 1, currentPosition.y + 1), turn + 1, history,
              loop(Position(currentPosition.x - 1, currentPosition.y), turn + 1, history,
                loop(Position(currentPosition.x, currentPosition.y), turn + 1, history,
                  loop(Position(currentPosition.x + 1, currentPosition.y), turn + 1, history,
                    loop(Position(currentPosition.x - 1, currentPosition.y - 1), turn + 1, history,
                      loop(Position(currentPosition.x, currentPosition.y - 1), turn + 1, history,
                        loop(Position(currentPosition.x + 1, currentPosition.y - 1), turn + 1, history,
                          bestRoute)))))))))
      }


      def brsetloop(turn: Int, positions: List[Route], bestRoute: Route): Route = {
        lazy val bestLength =
          if (bestRoute.value.nonEmpty) bestRoute.value.foldLeft(0d){case (c, pos) => c + placeCost(pos)}
          else 99d

        def newPositions(currentPosition: Position) = List(Position(currentPosition.x - 1, currentPosition.y + 1),
          Position(currentPosition.x, currentPosition.y + 1),
          Position(currentPosition.x + 1, currentPosition.y + 1),
          Position(currentPosition.x - 1, currentPosition.y),
          Position(currentPosition.x, currentPosition.y),
          Position(currentPosition.x - 1, currentPosition.y - 1),
          Position(currentPosition.x, currentPosition.y - 1),
          Position(currentPosition.x + 1, currentPosition.y - 1)
        )

        val spread: List[Route] = if (positions.isEmpty) newPositions(from).map(p => Route(List(p)))
        else positions.flatMap{ route =>
          val last = route.value.last
          newPositions(last).map(p => Route(route.value :+ p))
        }


        if (turn > limit) bestRoute
        else if (route.contains(currentPosition)) bestRoute
        else if (limit - turn < Math.abs(to.x - currentPosition.x)
          || limit - turn < Math.abs(to.y - currentPosition.y)) bestRoute
        else if (AreaPathing.isOutOfBounds(a, currentPosition)) bestRoute
        else if (AreaPathing.isInvalidTerran(a, currentPosition)) bestRoute
        else if (turn == 1 && AreaPathing.isInvalidMove(a, currentPosition)) bestRoute
        else if (currentPosition == to) selectRoute(Route(route :+ currentPosition), bestRoute, bestLength)
        else brsetloop(turn + 1, positions, bestRoute)
      }

      brsetloop(0, List.empty, Route(List.empty))

      loop(currentPosition = from, turn = 0, route = List.empty, bestRoute = Route(List.empty))
    }
  }
}