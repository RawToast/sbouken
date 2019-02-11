package bouken

import cats.MonadError

package object services {

  sealed trait GameError extends Throwable

  sealed trait ManagementError extends GameError
  object ManagementError {
    case object FailedToCreateGame extends ManagementError
    case object InitialLevelDoesNotExist extends ManagementError
    case object GameDoesNotExist extends ManagementError
  }

  type ManagementMonadError[F[_]] = MonadError[F, ManagementError]

  implicit class MonadErrorEx[A[_], E](me: MonadError[A, E]) {
    def fromOption[B](option: Option[B], error: E): A[B] =
      me.fromEither(option.toRight(error))
  }
}
