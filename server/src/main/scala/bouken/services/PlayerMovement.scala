package bouken.services

import bouken.domain.{Game, Player}
import cats.data.Chain
import cats.mtl.{FunctorTell, MonadState}

abstract class PlayerMovement[F[_]] {
  import PlayerMovement._

  def takeInput(move: Move)(
    implicit
    S: MonadState[F, Player],
    T: FunctorTell[F, Chain[String]]
  ): F[Game]

  def describeSurroundings()(
    implicit
    S: MonadState[F, Player],
    T: FunctorTell[F, Chain[String]]
  ): F[Unit]

  def tileEffect()(
    implicit S: MonadState[F, Player],
    T: FunctorTell[F, Chain[String]]
  ): F[Game]
}

object PlayerMovement {
  sealed trait Move
  case object North extends Move
  case object NorthEast extends Move
  case object NorthWest extends Move
  case object East extends Move
  case object West extends Move
  case object South extends Move
  case object SouthEast extends Move
  case object SouthWest extends Move
  case object Wait extends Move
}
