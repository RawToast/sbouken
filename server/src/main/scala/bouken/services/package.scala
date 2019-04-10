package bouken

import cats.MonadError

package object services {

  sealed trait GameError extends Throwable

  sealed trait GameLoopError extends GameError
  case object  InvalidGameStructure extends GameLoopError

  sealed trait ManagementError extends GameError
  object ManagementError {
    case object FailedToCreateGame extends ManagementError
    case object InitialLevelDoesNotExist extends ManagementError
    case object GameDoesNotExist extends ManagementError
  }

  sealed trait MovementError extends GameError
  case object IllegalMove extends MovementError

  type GameLoopMonadError[F[_]] = MonadError[F, GameLoopError]

  type ManagementMonadError[F[_]] = MonadError[F, ManagementError]

  type MovementMonadError[F[_]] = MonadError[F, MovementError]

  implicit class MonadErrorEx[A[_], E](me: MonadError[A, E]) {
    def fromOption[B](option: Option[B], error: E): A[B] =
      me.fromEither(option.toRight(error))
  }
}
