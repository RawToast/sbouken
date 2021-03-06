package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world._
import cats.syntax.functor._
import cats.syntax.flatMap._

import scala.collection.concurrent.TrieMap

abstract class GameManager[F[_]: ManagementMonadError] {
  def createGame(playerName: String, directory: String, uuid: UUID): F[Game]

  def saveGame(game: Game): F[Unit]

  def loadGame(uuid: UUID): F[Game]
}

object GameManager {
  def inMemory[F[_]](implicit MonadError: ManagementMonadError[F]): InMemoryGameManager[F] = {
    val areaParser = OptionAreaParser(OptionPlaceParser)
    val levelParser = OptionLevelParser(areaParser)
    val worldParser = OptionWorldParser(levelParser)

    InMemoryGameManager(worldParser)
  }
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

    def findPlayer(level: Level) =
      ErrorMonad.fromOption(
        level.area.value.values
          .find(_.state.isInstanceOf[Player])
          .flatMap(occ => occ.state match {
            case x: Player  => Some(x)
            case _          => None
          }), FailedToCreateGame)

    for {
      world <- makeWorld(directory)
      _ <- getCurrentLevel(world)
//      pla   <- findPlayer(level)
    } yield Game(
      uuid = uuid,
      player = Player(playerName, Health(10), PlayerLevelMeta(Position(1, 2), TimeDelta(0d))),
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
