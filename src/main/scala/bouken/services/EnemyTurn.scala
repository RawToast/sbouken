package bouken.services

import bouken.domain._
import bouken.syntax.pathing._
import bouken.syntax.vision._
import bouken.instances.SightInstances.EnemySight
import bouken.instances.VisionInstances.AreaVision

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.data.Chain
import cats.mtl.{ApplicativeAsk, FunctorTell, MonadState}
import com.olegpy.meow.hierarchy.{deriveApplicativeAsk => _, deriveApplicativeLocal => _, _}

abstract class EnemyTurn[F[_]: Monad] {

  def canSeePlayer(enemy: Enemy, position: Position)(
    implicit
    PlayerAsk: ApplicativeAsk[F, Player],
    LevelAsk: ApplicativeAsk[F, Level]
  ): F[Boolean] =
    for {
      level: Level <- LevelAsk.ask
      player: Player <- PlayerAsk.ask
      sightRange = enemy.range
      canSee = if (inRange(sightRange, position, player.meta.position))
        level.area.updateVisibility(enemy, position).value.get(position).exists(_.visible)
      else false
    } yield canSee

  def canAttackPlayer(enemy: Enemy, position: Position)(
    implicit
    PlayerAsk: ApplicativeAsk[F, Player]
    //    LevelAsk: ApplicativeAsk[F, Level] // This may be required for ranged enemies
  ): F[Boolean] =
    for {
      player <- PlayerAsk.ask
      nearby = inRange(1, position, player.meta.position)
    } yield nearby

  def findBestMove(enemy: Enemy, from: Position, to: Position)(
    LevelAsk: ApplicativeAsk[F, Level]
  ): F[Position] =
    for {
      level <- LevelAsk.ask
      _ = level.area.canNavigate(from, to, enemy.range.toInt) // Better limits, don't use sight
    } yield Position(0, 0)

  def move(enemy: Enemy, position: Position)(
    implicit
    LevelState: MonadState[F, Level]
  ): F[Unit] = // Maybe this can fail?
    Monad[F].unit

  def attack(enemy: Enemy, position: Position)(
    implicit
    PlayerState: MonadState[F, Player],
    Log: FunctorTell[F, Chain[String]]
  ): F[Unit] =
    for {
      p <- PlayerState.get
      _ <- Log.tell(Chain.one(s"${enemy.kind.toString} attacks ${p.name}!"))
      _ <- PlayerState.modify(p => p.copy(health = Health(p.health.value - 1))) // dummy for now
    } yield Unit


  private def inRange(range: Double, from: Position, to: Position) = {
    ((from.x + range) - to.x <= 0) ||
      ((from.x - range) - to.x <= 0) ||
      ((from.y + range) - to.y <= 0) ||
      ((from.y - range) - to.y <= 0)
  }
}