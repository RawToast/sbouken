package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.{Functor, Monad, MonadError}
import cats.syntax.functor._
import cats.syntax.flatMap._

abstract class GameManagerAlgebra[F[_]: ManagementMonadError] {
  def createGame(playerName: String, directory: String, uuid: UUID): F[Game]

  def saveGame(game: Game): F[Unit]

  def loadGame(uuid: UUID): F[Option[Game]]
}

case class GameManager[F[_]: ManagementMonadError: Functor](
  worldParser: WorldParser[Option]) extends GameManagerAlgebra[F] {

  implicit class ErrOption[A](option: Option[A]) {
    def raise(error: ManagementError)(implicit ME: MonadError[F, ManagementError]): F[A] = option match {
      case Some(value) => ME.pure[A](value)
      case None        => ME.raiseError[A](error)
    }
  }

  implicit class MonadErrorEx[A[_], E](me: MonadError[A, E]) {
    def fromOption[B](option: Option[B], error: E): A[B] =
      me.fromEither(option.toRight(error))
  }

  val ErrorMonad: ManagementMonadError[F] = implicitly[ManagementMonadError[F]]
  implicit val F: Monad[F] = implicitly

  def createGame(playerName: String, directory: String, uuid: UUID): F[Game] = {
    def makeWorld(directory: String): F[World] =
    ErrorMonad.fromOption(worldParser.parseWorld(directory), ManagementError.FailedToCreateGame)

    def getCurrentLevel(world: World): F[Level] =
    ErrorMonad.fromOption(world.currentLevel, ManagementError.InitialLevelDoesNotExist)

    for {
      world <- makeWorld(directory)
      _     <- getCurrentLevel(world)
    } yield Game(
      uuid = uuid,
      player = Player(playerName, Health(10), PlayerLevelMeta(Position(1, 1), TimeDelta(0d))),
      timeLines = TimeLineStore(Map.empty),
      world = world
    )
  }

  def saveGame(game: Game): F[Unit] =
    ???

  def loadGame(uuid: UUID): F[Option[Game]] = ???
}
