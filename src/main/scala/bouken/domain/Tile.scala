package bouken.domain

sealed trait Tile

case object Ground extends Tile

case object Rough extends Tile

case object Water extends Tile

case object Wall extends Tile

case class Stairs(to: To) extends Tile

case class Exit(score: Score) extends Tile

case class Score(value: Int) extends AnyVal

case class To(id: Int, level: String)