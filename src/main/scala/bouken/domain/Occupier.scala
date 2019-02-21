package bouken.domain

import bouken.domain.EnemyKind._

sealed trait Occupier
case object Empty extends Occupier

case class Player(name: String, health: Health, meta: PlayerLevelMeta) extends Occupier
case class Enemy(kind: EnemyKind, health: Health) extends Occupier

object Enemy {
  def apply(kind: EnemyKind): Enemy =
    kind match {
      case Zombie   => new Enemy(Zombie, Health(5))
      case Gnoll    => new Enemy(Gnoll, Health(3))
      case Minotaur => new Enemy(Minotaur, Health(10))
    }
}

case class Health(value: Int) extends AnyVal
