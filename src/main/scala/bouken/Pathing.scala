package bouken

import simulacrum._

import bouken.domain.{Place, Route}

case class Position(x: Int, y: Int)

trait Pathing[A] {
  def canNavigate(a: A, from: Position, to: Position, limit: Int): Boolean

  def isOutOfBounds(a: A, position: Position): Boolean

  def isInvalidTerran(a: A, position: Position): Boolean

  def isInvalidMove(a: A, position: Position): Boolean
}

object PathingSyntax {
  implicit class PathingSyntaxOps[A](val a: A) extends AnyVal {
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

@typeclass trait Navigation[A] {
  def suggestRoute[B: MoveCosts](a: A, mover: B, from: Position, to: Position)(implicit pathing: Pathing[A]): Route
}

trait LocatePosition[A, B] {
  def find(a: A, position: Position): B
}

object LocatePositionSyntax {
  implicit class LocatePositionSyntaxOps[A](a: A) {
    def find[B](position: Position)(implicit loc: LocatePosition[A, B]): B =
      loc.find(a, position)
  }
}

trait MoveCosts[B] {
  def moveCost(b: B, place: Place): Double
}
