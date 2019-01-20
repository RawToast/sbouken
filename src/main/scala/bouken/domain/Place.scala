package bouken.domain

case class Place(visible: Boolean, tile: Tile, state: Occupier, tileEffect: TileEffect)

sealed trait Occupier
case object Empty extends Occupier
case class Player(name: String, health: Health) extends Occupier
case class Enemy(kind: EnemyKind, health: Health) extends Occupier

object Enemy {
  def apply(kind: EnemyKind): Enemy =
    kind match {
      case Zombie => new Enemy(Zombie, Health(5))
      case Gnoll => new Enemy(Gnoll, Health(3))
      case Minotaur => new Enemy(Minotaur, Health(10))
    }
}

case class Health(value: Int) extends AnyVal

sealed trait EnemyKind

case object Zombie extends EnemyKind
case object Gnoll extends EnemyKind
case object Minotaur extends EnemyKind
