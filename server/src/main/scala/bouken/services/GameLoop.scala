package bouken.services

import bouken.domain.GameView
import cats.data.Chain
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.mtl.{FunctorTell, MonadState}
import cats.mtl.hierarchy.base._
import com.olegpy.meow.hierarchy.deriveMonadState


case class GameLoop[F[_] : MovementMonadError](
  playerMovement: PlayerMovement[F],
  timeLine: LevelTimeLine[F]) {

  def attempt(move: PlayerMovement.Move)(
    implicit
    S: MonadState[F, GameView],
    T: FunctorTell[F, Chain[String]]
  ): F[Unit] = {
    for {
      gv <- S.get
      lvl = gv.level.name
      timeline <- timeLine.get(lvl)
      _ <- playerMovement.takeInput(move)
      _ <- playerMovement.describeSurroundings()
      _ <- playerMovement.tileEffect()
      //      _ = timeLine.up
    } yield ()
  }
}