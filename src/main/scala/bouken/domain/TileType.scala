package bouken.domain

import cats.Show

sealed trait TileType

case object Blank extends TileType

case object Ground extends TileType

case object Rough extends TileType

case object Water extends TileType

case object Wall extends TileType

case class Stairs(to: To) extends TileType

case class Exit(score: Score) extends TileType

case class Score(value: Int) extends AnyVal

case class To(id: Int, level: String)

object TileType{
  implicit val show: Show[TileType] = Show.show {
    case Blank      => " "
    case Ground      => "."
    case Rough       => ":"
    case Water       => ";"
    case Wall        => "#"
    case Stairs(_)   => "/"
    case Exit(_)     => "e"
  }
}
