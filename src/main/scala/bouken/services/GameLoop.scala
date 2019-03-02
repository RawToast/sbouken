package bouken.services

import bouken.domain.GameView
import cats.Monad
import cats.data.Chain
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.mtl.{FunctorTell, MonadState}
import com.olegpy.meow.hierarchy.{deriveApplicativeAsk => _, deriveApplicativeLocal => _, _}

// Not required, leaving for reference, this is sometimes required
// import cats.mtl.hierarchy.base._

case class GameLoop[F[_] : Monad](
  enemyTurn: EnemyTurn[F],
  playerMovement: PlayerMovement[F],
  timeLine: LevelTimeLine[F]) {

  def attempt()(
    implicit
    S: MonadState[F, GameView],
    T: FunctorTell[F, Chain[String]]
  ): F[Unit] = {
    for {
      lvl <- S.get.map(_.level.name)
      timeline <- timeLine.get(lvl)
      _ <- playerMovement.takeInput()
      _ <- playerMovement.describeSurroundings()
      _ <- playerMovement.tileEffect()
      //      _ = timeLine.up
    } yield ()
  }


}