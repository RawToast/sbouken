package bouken.domain

import cats.Show

sealed trait Tile

case object Ground extends Tile

case object Rough extends Tile

case object Water extends Tile

case object Wall extends Tile

case class Stairs(to: To) extends Tile

case class Exit(score: Score) extends Tile

case class Score(value: Int) extends AnyVal

case class To(id: Int, level: String)

object Tile{
  implicit val show: Show[Tile] = Show.show {
    case Ground      => "."
    case Rough       => ":"
    case Water       => ";"
    case Wall        => "#"
    case Stairs(_)   => "/"
    case Exit(_)     => "e"
  }
}
