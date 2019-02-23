package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.syntax.functor._
import cats.syntax.flatMap._

import scala.collection.concurrent.TrieMap

abstract class GameManager[F[_]: ManagementMonadError] {
  def createGame(playerName: String, directory: String, uuid: UUID): F[Game]

  def saveGame(game: Game): F[Unit]

  def loadGame(uuid: UUID): F[Game]
}

case class InMemoryGameManager[F[_]: ManagementMonadError](
  worldParser: WorldParser[Option]) extends GameManager[F] {
  import bouken.services.ManagementError._

  private val store = new TrieMap[UUID, Game]

  val ErrorMonad: ManagementMonadError[F] = implicitly[ManagementMonadError[F]]

  def createGame(playerName: String, directory: String, uuid: UUID): F[Game] = {
    def makeWorld(directory: String): F[World] =
    ErrorMonad.fromOption(worldParser.parseWorld(directory), FailedToCreateGame)

    def getCurrentLevel(world: World): F[Level] =
    ErrorMonad.fromOption(world.currentLevel, InitialLevelDoesNotExist)

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

  def saveGame(game: Game): F[Unit] = {
    store.put(game.uuid, game)
    ErrorMonad.unit
  }

  def loadGame(uuid: UUID): F[Game] = {
    ErrorMonad.fromOption(store.get(uuid), GameDoesNotExist)
  }
}
