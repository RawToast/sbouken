package bouken.instances

import bouken.types.Sight.ops._
import bouken.domain.{Area, Place, Position}
import bouken.types.{Sight, Visibility}

import scala.annotation.tailrec

object VisionInstances {

  implicit val AreaVision: Visibility[Area] = new Visibility[Area] {

    override def updateVisibility[S: Sight](a: Area, s: S, position: Position): Area = {

      val lines = makeLines(s, a, position)

      val rr: Map[Position, Place] = a.value
        .withFilter(pp => lines.contains(pp._1))
        .map(pp => (pp._1, pp._2.copy(visible = true)))

      Area(a.value ++ rr)
    }

    private def makeLines[S : Sight](s: S, area: Area, originalPosition: Position): Set[Position] = {
      val sight: Sight[S] = implicitly[Sight[S]]

      val rng = List.range(-sight.range(s).toInt, sight.range(s).toInt + 1).filter(_ != 0)

      rangeToPositions(rng)
        .flatMap(makeLine(s, area, originalPosition, _))
        .toSet
        .filter(pos => (pos.x <= originalPosition.x + sight.range(s).toInt) && (pos.y <= originalPosition.y + sight.range(s).toInt))
        .filter(pos => (pos.x >= originalPosition.x - sight.range(s).toInt) && (pos.y >= originalPosition.y - sight.range(s).toInt))
    }

    //makeLine(limit + 2, area, (x, y), dxdy))
    private def makeLine[S : Sight](s: S, area: Area, originalPosition: Position, deltaPosition: Position): List[Position] = {
      val (ffx, ffy) = // Int => Int, Int => Int
        (
          if (deltaPosition.x == 0) (x: Int) => x
          else if (deltaPosition.x > 0) (x: Int) => x + 1
          else (x: Int) => x - 1
          ,
          if (deltaPosition.y == 0) (y: Int) => y
          else if (deltaPosition.y > 0) (y: Int) => y + 1
          else (y: Int) => y - 1
        )


      @tailrec
      def loop(i: Double, ix: Int, iy: Int, acc: List[Position]): List[Position] = {
        val nx = ffx(ix)
        val ny = ffy(iy)

        if (i > s.range) acc
        else if (acc.isEmpty) loop(i, ix, iy, List(originalPosition))
        else if (nx == deltaPosition.x && ny == deltaPosition.y) {
          val head = acc.head
          val newPosition: Position = Position(ffx(head.x), ffy(head.y))
          val cost: Double = area.value.get(newPosition).map(s.visionCost).getOrElse(99d)
          if (i + cost > s.range) newPosition +: acc
          else loop(i + cost, 0, 0, newPosition +: acc)
        }
        else if (nx == deltaPosition.x && deltaPosition.y != 0) {
          val head = acc.head
          val newPosition: Position = Position(ffx(head.x), head.y)
          val cost: Double = area.value.get(newPosition).map(s.visionCost).getOrElse(99d)
          if (i + cost > s.range) newPosition +: acc
          else loop(i + cost, 0, ny, newPosition +: acc)
        }
        else if (ny == deltaPosition.y) {
          val head = acc.head
          val newPosition: Position = Position(head.x, ffy(head.y))
          val cost: Double = area.value.get(newPosition).map(s.visionCost).getOrElse(99d)
          if (i + cost > s.range) newPosition +: acc
          else loop(i + cost, nx, ny, newPosition +: acc)
        }
        else loop(i, nx, ny, acc)
      }

      loop(0, 0, 0, List.empty)
    }

    private def rangeToPositions(rng: List[Int]): List[Position] =
      List(Position(0, 1), Position(0, -1), Position(1, 0), Position(-1, 0)) ++ (
          for {
            x <- rng
            y <- rng
          } yield Position(x, y)
        )
  }
}
