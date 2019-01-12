package bouken

import bouken.domain.{Place, Tile}

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

  def canSeeThrough(b: B, tile: Tile): Boolean

  val range: Int
}
