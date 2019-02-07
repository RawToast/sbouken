package bouken

package object services {

  sealed trait GameError extends Throwable

  sealed trait GameManagementError extends GameError
  object GameManagementError {
    case object FailedToCreateGame extends GameManagementError
    case class System(error: Throwable) extends GameManagementError
  }
}
