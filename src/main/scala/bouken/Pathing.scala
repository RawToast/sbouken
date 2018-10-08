package bouken

import scala.annotation.tailrec
import scala.util.control.TailCalls.TailRec

trait Pathing[A] {
  def invalidPosition(position: Position): Boolean =
    position.x < 0 || position.y < 0

  def canNavigate(a: A, from: Position, to: Position, limit: Int): Boolean

  def isOutOfBounds(a: A, position: Position): Boolean

  def isInvalidTerran(a: A, position: Position): Boolean

  def isInvalidMove(a: A, position: Position): Boolean
}

case class Area(value: Map[Position, Place]) extends AnyVal

object Area {
  implicit val areaPathing: Pathing[Area] = new Pathing[Area] {
    override def canNavigate(a: Area, from: Position, to: Position, limit: Int): Boolean = {

      def loop(currentPosition: Position, turn: Int, route: Seq[Position], canNavi: Boolean): Boolean =
        if (turn > limit) false
        else if (canNavi) false
        else if (limit - turn < Math.abs(to.x - currentPosition.x)
          || limit - turn < Math.abs(to.y - currentPosition.y)) false
        else if (invalidPosition(currentPosition)) false
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
      a.value.get(position).isEmpty

    override def isInvalidTerran(a: Area, position: Position): Boolean =
      a.value.get(position).exists(_.tile == Wall)

    override def isInvalidMove(a: Area, position: Position): Boolean = {
      a.value.get(position) match {
        case Some(place) =>
          if (!Seq(Ground, Rough, Water).contains(place.tile)) true
          else if (place.state != Empty) true
          else false
        case None => true
      }
    }
  }
}


case class Position(x: Int, y: Int)

case class Place(visible: Boolean, tile: Tile, state: Occupier, tileEffect: TileEffect)

sealed trait Tile

case object Ground extends Tile

case object Rough extends Tile

case object Water extends Tile

case object Wall extends Tile

//case class Stairs(???) extends Tile
//case class Exit(???) extends Tile

sealed trait Occupier

case object Empty extends Occupier

//case class Player(player: ???)
//case object Enemy(enemy: ???)

sealed trait TileEffect

case object NoEffect extends TileEffect

case class Trap(damage: Int) extends TileEffect

case class Snare(duration: Int) extends TileEffect

case class Heal(amount: Int) extends TileEffect

case class Gold(amount: Int) extends TileEffect

//type area = list(list(place));