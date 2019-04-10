package bouken.services

import bouken.domain.GameView
import cats.Monad
import cats.data.Chain
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.mtl.{FunctorTell, MonadState}
import com.olegpy.meow.hierarchy.{deriveApplicativeAsk => _, deriveApplicativeLocal => _, _}
import cats.mtl.hierarchy.base._

case class GameLoop[F[_] : Monad](
  playerMovement: PlayerMovement[F],
  timeLine: LevelTimeLine[F]) {

  def attempt(move: PlayerMovement.Move)(
    implicit
    S: MonadState[F, GameView],
    T: FunctorTell[F, Chain[String]],
    MEL: MovementMonadError[F]
  ): F[Unit] = {
    for {
      gv <- S.get
      lvl = gv.level.name
      timeline <- timeLine.get(lvl)
      // Could use boolean or short-circuit here with ME
      moved <- playerMovement.takeInput(move)
      _ <- playerMovement.describeSurroundings()
      _ <- playerMovement.tileEffect()
      //      _ = timeLine.up
    } yield ()
  }
}