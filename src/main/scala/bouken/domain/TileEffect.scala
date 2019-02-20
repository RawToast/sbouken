package bouken.domain

import cats.Show

sealed trait TileEffect

case object NoEffect extends TileEffect
case class Trap(damage: Int) extends TileEffect
case class Snare(duration: Int) extends TileEffect
case class Heal(amount: Int) extends TileEffect
case class Gold(amount: Int) extends TileEffect

object TileEffect {
  implicit val show: Show[TileEffect] = Show.show[TileEffect] {
    case NoEffect   => ""
    case Trap(_)    => ","
    case Snare(_)   => ";"
    case Heal(_)    => "+"
    case Gold(_)    => "g"
  }
}
