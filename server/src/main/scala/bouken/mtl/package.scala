package bouken

import bouken.domain.{GameView, Level, Player}
import cats.Monad
import cats.mtl.MonadState
import com.softwaremill.quicklens._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.mtl.hierarchy.base._

package object mtl {
  implicit def LMStatus[F[_]: Monad](implicit S: MonadState[F, GameView]): MonadState[F, Player] = {
    new MonadState[F, Player] {
      override val monad: Monad[F] = S.monad

      override def get: F[Player] = S.get.map(_.player)

      override def set(s: Player): F[Unit] =
        S.modify(_.modify(_.player).setTo(s))

      override def inspect[A](f: Player => A): F[A] =
        S.inspect(gv => f(gv.player))

      override def modify(f: Player => Player): F[Unit] =
        S.modify(_.modify(_.player).using(f))
    }
  }

  implicit def MSLevel[F[_]: Monad](implicit S: MonadState[F, GameView]): MonadState[F, Level] = {
    new MonadState[F, Level] {
      override val monad: Monad[F] = S.monad

      override def get: F[Level] = S.get.map(_.level)

      override def set(s: Level): F[Unit] =
        S.modify(_.modify(_.level).setTo(s))

      override def inspect[A](f: Level => A): F[A] =
        S.inspect(gv => f(gv.level))

      override def modify(f: Level => Level): F[Unit] =
        S.modify(_.modify(_.level).using(f))
    }
  }
}
