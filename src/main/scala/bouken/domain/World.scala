package bouken.domain

import enumeratum._

case class World(current: Level.Name, levels: Map[Level.Name, Level])

case class Level(area: Area, name: Level.Name, tileSet: Level.TileSet)

object Level {
  case class Name(value: String) extends AnyVal

  sealed trait TileSet extends EnumEntry
  case object TileSet extends Enum[TileSet] with CirceEnum[TileSet] {
    case object Default extends TileSet

    val values = findValues
  }
}
