package bouken.domain

import enumeratum._

case class Level(area: Area, name: String, tileSet: TileSet)

sealed trait TileSet extends EnumEntry
case object TileSet extends Enum[TileSet] with CirceEnum[TileSet] {
  case object Default extends TileSet

  val values = findValues
}

