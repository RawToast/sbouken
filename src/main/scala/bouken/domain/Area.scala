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
        if(routeA.value.foldLeft(0d){ case (c, pos) => c + placeCost(pos) } < bestLength) routeA
        else routeB

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

      loop(currentPosition = from, turn = 0, route = List.empty, bestRoute = Route(List.empty))
    }
  }
}

object TrArea{
  // Scrappy tail-recursive implementation, but may be required later.
  val Navigation = new Navigation[Area] {
    import bouken.LocatePositionSyntax._
    override def suggestRoute[B: MoveCosts](a: Area, mover: B, from: Position, to: Position
                                           )(implicit pathing: Pathing[Area]): Route = {
      case class CostedRoute(a: Route, cost: Double)

      val limit = 9
      val costCalc: MoveCosts[B] = implicitly[MoveCosts[B]]
      def placeCost(place: Position) = a.find(place).map(costCalc.moveCost(mover, _)).getOrElse(99d)

      @scala.annotation.tailrec
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

        // Filter paths
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

        // Compare completed routes against best and find the new best
        val (complete, incomplete) = possibleRoutes.partition(_.value.contains(to))


        val updBest =
          if (complete.isEmpty) bestRoute.map(r => CostedRoute(r, bestLength))
          else bestRoute match {
            case Some(br) =>
              Option(
                complete.foldLeft(CostedRoute(br, bestLength))((ca: CostedRoute, b: Route) =>
                  if (b.value.foldLeft(0d) { case (c, pos) => c + placeCost(pos) } < ca.cost)
                    CostedRoute(b, b.value.foldLeft(0d) { case (c, pos) => c + placeCost(pos) })
                  else ca)
              )
            case None =>
              complete
                .map(r => CostedRoute(r, r.value.foldLeft(0d) { case (c, pos) => c + placeCost(pos) }))
                .reduceOption((a, b) => if(a.cost > b.cost) b else a)
          }

        val newBest: Option[Route] = updBest.map(_.a)
        val newBestCost = updBest.map(_.cost).getOrElse(99d)

        // Can any current routes possibly beat the best?
        lazy val potentialRoutes = incomplete.filter{ r =>
          newBestCost > r.value.foldLeft(0d){ case (c, pos) => c + placeCost(pos) }
        }

        if (potentialRoutes.isEmpty) newBest
        else bestloop(turn + 1, potentialRoutes, newBest)
      }

      bestloop(0, List.empty, None).getOrElse(Route(List.empty))
    }
  }
}
