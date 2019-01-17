package bouken.instances

import bouken.{Position, Sight, Visibility}
import bouken.domain.{Area, Place}

object VisionInstances {

  implicit val AreaVision: Visibility[Area] = new Visibility[Area] {

    override def updateVisibility[S: Sight](a: Area, s: S, position: Position): Area = {
      val sight: Sight[S] = implicitly[Sight[S]]

      val result = updateVisibility(a.value, position)

      Area(result)
    }

    private def updateVisibility(area: Map[Position, Place], position: Position) =
      area
        .get(position)
        .map(_.copy(visible = true))
        .fold(area)(p => area + (position -> p))

    private def makeLines[S](sight: Sight[S], originalPosition: Position): Set[Position] = {

      val rng = List.range(sight.range, sight.range + 1).filter(_ != 0)

      rangeToPositions(rng)
          .flatMap(makeLine(sight, ???, originalPosition, _))
          .toSet
          .filter(pos => (pos.x <= originalPosition.x + sight.range) && (pos.y <= originalPosition.y + sight.range))
          .filter(pos => (pos.x >= originalPosition.x - sight.range) && (pos.y >= originalPosition.y - sight.range))
    }

    //makeLine(limit + 2, area, (x, y), dxdy))
    private def makeLine[S](sight: Sight[S], area: Area, originalPosition: Position, deltaPosition: Position): List[Position] = {
      val (ffx, ffy) =
        (
          if (deltaPosition.x == 0) (x: Int) => x
          else if (deltaPosition.x > 0) (x: Int) => x+1
          else (x: Int) => x - 1
          ,
          if (deltaPosition.y == 0) (y: Int) => y
          else if (deltaPosition.y > 0) (y: Int) => y + 1
          else (y: Int) => y - 1
        )


//      let rec loop = (i, ix, iy, acc) => {
//        let nx = ffx(ix);
//        let ny = ffy(iy);
//        if (i > distance) acc
//        else if (List.length(acc) == 0) loop(i, ix, iy, [ (ox, oy), ... acc])
//        else if (nx == dx && ny == dy) {
//          let (hx, hy) = List.hd(acc);
//          if (cannotSeeThrough((ffx(hx), ffy(hy)))) [ (ffx(hx), ffy(hy)), ... acc]
//          else loop(i + 1, 0, 0, [ (ffx(hx), ffy(hy)), ... acc]) }
//        else if (nx == dx && dy != 0) {
//          let (hx, hy) = List.hd(acc);
//          if (cannotSeeThrough((ffx(hx), hy))) [ (ffx(hx), hy ), ... acc]
//          else loop(i + 1, 0, ny, [ (ffx(hx), hy ), ... acc]) }
//        else if (ny == dy) {
//          let (hx, hy) = List.hd(acc);
//          if (cannotSeeThrough(( hx, ffy(hy)))) [ (hx, ffy(hy)), ... acc]
//          else loop(i + 1, nx, 0, [ (hx, ffy(hy)), ... acc]) }
//        else loop(i, nx, ny, acc);
//      };
//
//      loop(0, 0, 0, []);

      ???
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
