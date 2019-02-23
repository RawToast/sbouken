package bouken.domain

import enumeratum._

sealed trait EnemyKind extends EnumEntry

case object EnemyKind extends Enum[EnemyKind] with CirceEnum[EnemyKind] {
  val values = findValues

  case object Zombie extends EnemyKind
  case object Gnoll extends EnemyKind
  case object Minotaur extends EnemyKind
}
