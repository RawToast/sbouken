package bouken.services

import bouken.domain.{Game, Player}
import cats.data.Chain
import cats.mtl.{FunctorTell, MonadState}

abstract class PlayerMovement[F[_]] {
  def takeInput()(
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
