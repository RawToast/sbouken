package bouken.domain

case class Level(area: Area, name: String, tileSet: TileSet)

sealed trait TileSet
case object Default extends TileSet