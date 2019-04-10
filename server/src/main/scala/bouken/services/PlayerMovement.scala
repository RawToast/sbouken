package bouken.services

import bouken.domain.{Game, Level, Player, Position}
import cats.Monad
import cats.data.Chain
import cats.syntax.applicative._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.mtl.{ApplicativeAsk, FunctorTell, MonadState}
import com.softwaremill.quicklens._

abstract class PlayerMovement[F[_]: Monad] {
  import PlayerMovement._

  def takeInput(move: Move)(
    implicit
    T: FunctorTell[F, Chain[String]],
    L: ApplicativeAsk[F, Level],
    P: MonadState[F, Player],
    ME: MovementMonadError[F]
  ): F[Boolean] = for {
    player <- P.get
    position = calculatePosition(player.meta.position, move)
    level = L.ask
    result = true
    _ <- if (!result) ME.raiseError[Boolean](IllegalMove) else ME.pure(result)
  } yield result

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

  def calculatePosition(position: Position, move: Move): Position =
    move match {
      case North     => position.modify(_.y).using(_ + 1)
      case NorthEast => position.modify(_.y).using(_ + 1).modify(_.x).using(_ + 1)
      case NorthWest => position.modify(_.y).using(_ + 1).modify(_.x).using(_ - 1)
      case East      => position.modify(_.x).using(_ + 1)
      case West      => position.modify(_.x).using(_ - 1)
      case South     => position.modify(_.y).using(_ - 1)
      case SouthEast => position.modify(_.y).using(_ - 1).modify(_.x).using(_ + 1)
      case SouthWest => position.modify(_.y).using(_ - 1).modify(_.x).using(_ - 1)
      case Wait      => position
    }
}
