package bouken.domain

import cats.Show
import enumeratum._

sealed trait EnemyKind extends EnumEntry

case object EnemyKind extends Enum[EnemyKind] with CirceEnum[EnemyKind] {
  val values = findValues

  case object Zombie extends EnemyKind
  case object Gnoll extends EnemyKind
  case object Minotaur extends EnemyKind

  implicit val show: Show[EnemyKind] = Show.show[EnemyKind] {
    case Zombie   => "Z"
    case Gnoll    => "X"
    case Minotaur => "M"
  }
}
