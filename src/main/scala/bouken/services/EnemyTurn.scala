package bouken.services

import bouken.domain._
import cats._
import cats.data.Chain
import cats.mtl.{ApplicativeAsk, FunctorTell, MonadState}

abstract class EnemyTurn[F[_]: Monad] {

  def canSeePlayer(enemy: Enemy, position: Position)(
    implicit
    PlayerAsk: ApplicativeAsk[F, Player],
    LevelAsk: ApplicativeAsk[F, Level]
  ): F[Boolean]

  def canAttackPlayer(enemy: Enemy, position: Position)(
    implicit
    PlayerAsk: ApplicativeAsk[F, Player]
  ): F[Boolean]

  def findBestMove(enemy: Enemy, from: Position, to: Position)(
    LevelAsk: ApplicativeAsk[F, Level]
  ): F[Position]

  def move(enemy: Enemy, position: Position)(
    implicit
    LevelState: MonadState[F, Level]
  ): F[Unit]

  def attack(enemy: Enemy, position: Position)(
    implicit
    PlayerState: MonadState[F, Player],
    Log: FunctorTell[F, Chain[String]]
    ): F[Unit]
}
