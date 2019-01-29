package bouken

import bouken.domain.{Place, Position, Route}
import simulacrum._

@typeclass trait Pathing[A] {
  def canNavigate(a: A, from: Position, to: Position, limit: Int): Boolean

  def isOutOfBounds(a: A, position: Position): Boolean

  def isInvalidTerran(a: A, position: Position): Boolean

  def isInvalidMove(a: A, position: Position): Boolean
}

@typeclass trait Navigation[A] {
  def suggestRoute[B: MoveCosts](a: A, mover: B, from: Position, to: Position)(implicit pathing: Pathing[A]): Route
}

trait LocatePosition[A, B] {
  def find(a: A, position: Position): B
}

object LocatePosition {
  object ops {
    implicit class LocatePositionSyntaxOps[A](val a: A) extends AnyVal {
      def find[B](position: Position)(implicit loc: LocatePosition[A, B]): B =
        loc.find(a, position)
    }
  }
}

@typeclass trait MoveCosts[B] {
  def moveCost(b: B, place: Place): Double
}
