package bouken.domain

sealed trait TileType


case object Ground extends TileType

case object Rough extends TileType

case object Water extends TileType

case object Wall extends TileType

case class Stairs(to: To) extends TileType

case class Exit(score: Score) extends TileType

case class Score(value: Int) extends AnyVal

case class To(id: Int, level: String)
