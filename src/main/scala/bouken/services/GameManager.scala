package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.Monad

abstract class GameManagerAlgebra[F[_]: GameManagerMonadError] {
  def createGame(playerName: String, directory: String): F[Game]

  def saveGame(game: Game): F[Unit]

  def loadGame(uuid: UUID): F[Option[Game]]
}

case class GameManager[F[_]: GameManagerMonadError](
  worldParser: WorldParser[Option]) extends GameManagerAlgebra[F] {

  val ErrorMonad: GameManagerMonadError[F] = implicitly

  def createGame(playerName: String, directory: String): F[Game] = {
    worldParser.parseWorld(directory)
      .map{world =>
        Game(
          uuid = UUID.randomUUID(), // No good
          player = Player(playerName, Health(10), PlayerLevelMeta(???, ???)),
          timeLines = TimeLineStore(Map.empty),
          world = world)
      } match {
      case Some(value)  => ErrorMonad.pure(value)
      case None         => ErrorMonad.raiseError(GameManagementError.FailedToCreateGame)
    }
  }

  def saveGame(game: Game): F[Unit] =
    implicitly[Monad[F]].pure(())

  def loadGame(uuid: UUID): F[Option[Game]] = ???
}
