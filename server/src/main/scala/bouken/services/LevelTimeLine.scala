package bouken.services

import bouken.domain._
import cats.Monad
import cats.mtl.MonadState

abstract class LevelTimeLine[F[_]: Monad]{
  def create(levelName: Level.Name): F[TimeLine]

  def get(levelName: Level.Name): F[TimeLine]

  def activate(position: Position): F[TimeLine]

  def update(position: Position, actor: Actor): F[TimeLine]

  def updateUntilActive()(
    implicit
    S: MonadState[F, Player]
  ): F[(TimeLine, TimeDelta)]
}
