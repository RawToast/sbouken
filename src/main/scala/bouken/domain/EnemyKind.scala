package bouken.domain

import cats.Show

sealed trait EnemyKind

case object Zombie extends EnemyKind
case object Gnoll extends EnemyKind
case object Minotaur extends EnemyKind

object EnemyKind {
  implicit val show: Show[EnemyKind] = Show.show[EnemyKind] {
    case Zombie   => "Z"
    case Gnoll    => "X"
    case Minotaur => "M"
  }
}
