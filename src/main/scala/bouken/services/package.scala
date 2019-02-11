package bouken

import cats.MonadError

package object services {

  sealed trait GameError extends Throwable

  sealed trait ManagementError extends GameError
  object ManagementError {
    case object FailedToCreateGame extends ManagementError
    case object InitialLevelDoesNotExist extends ManagementError
    case object PlayerDoesNotExist extends ManagementError
  }

  type ManagementMonadError[F[_]] = MonadError[F, ManagementError]
}
