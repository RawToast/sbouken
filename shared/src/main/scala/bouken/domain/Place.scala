package bouken.domain

case class Place(visible: Boolean, tile: Tile, state: Occupier, tileEffect: TileEffect)

sealed trait Occupier
case object Empty extends Occupier

//case class Player(player: ???)
//case object Enemy(enemy: ???)

