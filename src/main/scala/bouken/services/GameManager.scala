package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.{Monad, MonadError}

trait GameManagerAlgebra[F[_]] {
  def createGame(playerName: String, directory: String)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Game]

  def saveGame(game: Game)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Unit]

  def loadGame(uuid: UUID)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Option[Game]]
}

case class GameManager[F[_]](worldParser: WorldParser[Option]) extends GameManagerAlgebra[F] {
  def createGame(playerName: String, directory: String)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Game] = {
    worldParser.parseWorld(directory)
      .map{world =>
        Game(
          uuid = UUID.randomUUID(), // No good
          player = Player(playerName, Health(10), PlayerLevelMeta(???, ???)),
          timeLines = TimeLineStore(Map.empty),
          world = world)
      } match {
      case Some(value)  => E.pure(value)
      case None         => E.raiseError(GameManagementError.FailedToCreateGame)
    }

  }

  def saveGame(game: Game)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Unit] = ???

  def loadGame(uuid: UUID)(
    implicit
    E: MonadError[F, GameManagementError]
  ): F[Option[Game]] = ???
}
