package bouken.domain

case class Place(visible: Boolean, tile: TileType, state: Occupier, tileEffect: TileEffect)

case class PlayerLevelMeta(position: Position, timeDelta: TimeDelta)
