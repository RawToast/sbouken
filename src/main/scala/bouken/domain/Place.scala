package bouken.domain

import cats.Show
import cats.syntax.show._

case class Place(visible: Boolean, tile: Tile, state: Occupier, tileEffect: TileEffect)
object Place {

  implicit val show: Show[Place] = Show.show { place =>
    if (place.visible) {
      place.state match {
        case Empty => place.tile.show
        case Player(_, _, _)     => "O"
        case Enemy(kind, _)      => kind.show
      }
    } else ""
  }
}

case class PlayerLevelMeta(position: Position, timeDelta: TimeDelta)
