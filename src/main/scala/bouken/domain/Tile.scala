package bouken.domain

sealed trait Tile

case object Ground extends Tile

case object Rough extends Tile

case object Water extends Tile

case object Wall extends Tile

//case class Stairs(???) extends Tile
//case class Exit(???) extends Tile