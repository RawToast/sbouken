package bouken

case class Position(x: Int, y: Int)

trait Pathing[A] {
  def canNavigate(a: A, from: Position, to: Position, limit: Int): Boolean

  def isOutOfBounds(a: A, position: Position): Boolean

  def isInvalidTerran(a: A, position: Position): Boolean

  def isInvalidMove(a: A, position: Position): Boolean
}

object PathingSyntax {
  implicit class PathingSyntaxOps[A](a: A) {
    def isOutOfBounds(position: Position)(implicit pathing: Pathing[A]): Boolean =
      pathing.isOutOfBounds(a, position)

    def isInvalidTerran(position: Position)(implicit pathing: Pathing[A]): Boolean =
      pathing.isInvalidTerran(a, position)

    def isInvalidMove(position: Position)(implicit pathing: Pathing[A]): Boolean =
      pathing.isInvalidMove(a, position)

    def canNavigate(from: Position, to: Position, limit: Int)(implicit pathing: Pathing[A]): Boolean =
      pathing.canNavigate(a, from, to, limit)
  }
}
