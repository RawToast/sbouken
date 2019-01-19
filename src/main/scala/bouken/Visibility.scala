package bouken

import bouken.domain.Place

trait Visibility[A] {
  def updateVisibility[S: Sight](a: A, s: S, position: Position): A
}

object VisibilitySyntax {
  implicit class VisibilitySyntaxOps[A](val a: A) extends AnyVal {
    def updateVisibility[S: Sight](s: S, position: Position)(implicit sight: Visibility[A]): A =
      sight.updateVisibility(a, s, position)
  }
}

trait Sight[B] {
  def visionCost(b: B, place: Place): Double

  val range: Double
}

object SightSyntax {
  implicit class SightSyntaxOps[A](val a: A) extends AnyVal {
    def range(implicit S: Sight[A]): Double =
      S.range

    def visionCost(place: Place)(implicit S: Sight[A]): Double =
      S.visionCost(a, place)
  }
}
