package bouken.instances

import bouken.domain._
import bouken.domain.EnemyKind._
import bouken.types.Sight

object SightInstances {
  implicit val EnemySight: Sight[Enemy] = new Sight[Enemy] {
    override def visionCost(b: Enemy, place: Place): Double =
      b.kind match {
        case Minotaur => tallSight(place.tile)
        case _ => defaultSight(place.tile)
      }

    override def range(b: Enemy): Double =
      b.kind match {
        case Minotaur => 9d
        case Gnoll => 6d
        case Zombie => 5d
      }

    private def defaultSight(tile: TileType) = tile match {
      case Ground => 1.0
      case Rough => 1.5
      case Water => 1.0
      case Wall => 99d
      case Stairs(_) => 1d
      case Exit(_) => 1d
    }

    private def tallSight(tile: TileType) = tile match {
      case Ground => 1.0
      case Rough => 1.0
      case Water => 1.0
      case Wall => 99d
      case Stairs(_) => 1d
      case Exit(_) => 1d
    }
  }
}
