package bouken

package object services {

  sealed trait GameError extends Throwable

  sealed trait GameManagementError extends GameError
  case object FailedToCreateGame extends GameManagementError
}
