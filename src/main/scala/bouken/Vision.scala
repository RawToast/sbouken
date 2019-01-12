package bouken

import bouken.domain.{Place, Tile}

trait Vision[A] {
  def updateVisibility[S: Sight](a: A, s: S, position: Position): A
}

object VisionSyntax {
  implicit class VisionSyntaxOps[A](val a: A) extends AnyVal {
    def updateVisibility[S: Sight](s: S, position: Position)(implicit sight: Vision[A]): A =
      sight.updateVisibility(a, s, position)
  }
}

trait Sight[B] {
  def visionCost(b: B, place: Place): Double

  def canSeeThrough(b: B, tile: Tile): Boolean

  val range: Int
}
