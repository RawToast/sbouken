package bouken

import simulacrum._

import bouken.domain.Place

@typeclass trait Visibility[A] {
  def updateVisibility[S: Sight](a: A, s: S, position: Position): A
}

trait Sight[B] {
  def visionCost(b: B, place: Place): Double

  val range: Double
}

object Sight {
  object ops {
    implicit class Syntax[A](val a: A) extends AnyVal {
      def range(implicit S: Sight[A]): Double =
        S.range

      def visionCost(place: Place)(implicit S: Sight[A]): Double =
        S.visionCost(a, place)
    }
  }
}
