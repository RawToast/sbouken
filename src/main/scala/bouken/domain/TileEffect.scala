package bouken.domain

sealed trait TileEffect

case object NoEffect extends TileEffect
case class Trap(damage: Int) extends TileEffect
case class Snare(duration: Int) extends TileEffect
case class Heal(amount: Int) extends TileEffect
case class Gold(amount: Int) extends TileEffect
