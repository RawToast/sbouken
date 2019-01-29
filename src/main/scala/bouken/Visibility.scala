package bouken

import bouken.domain.{Place, Position}
import simulacrum._

@typeclass trait Visibility[A] {
  def updateVisibility[S: Sight](a: A, s: S, position: Position): A
}

trait Sight[B] {
  def visionCost(b: B, place: Place): Double

  def range(b: B): Double
}

object Sight {
  object ops {
    implicit class Syntax[A](val a: A) extends AnyVal {
      def range(implicit S: Sight[A]): Double =
        S.range(a)

      def visionCost(place: Place)(implicit S: Sight[A]): Double =
        S.visionCost(a, place)
    }
  }
}
