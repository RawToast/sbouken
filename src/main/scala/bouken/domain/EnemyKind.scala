package bouken.domain

import enumeratum._

sealed trait EnemyKind extends EnumEntry

case object EnemyKind extends Enum[EnemyKind] with CirceEnum[EnemyKind] {
  def fromPlace(place: Place): Option[EnemyKind] = place.state match {
    case Enemy(kind, _)      => Some(kind)
    case _ => None
  }

  val values = findValues

  case object Zombie extends EnemyKind
  case object Gnoll extends EnemyKind
  case object Minotaur extends EnemyKind
}
