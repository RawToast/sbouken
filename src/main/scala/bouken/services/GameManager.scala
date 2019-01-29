package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.implicits._
import cats.{Monad, MonadError}

abstract class GameManager[F[_] : Monad](worldParser: WorldParser[Option]) {
  def createGame(playerName: String)(
    implicit F:
    MonadError[F, GameManagementError]
  ): F[Game] =
    for {
      world <- F.fromOption(worldParser.parseWorld("world"), FailedToCreateGame)
      game = Game(
        uuid = ???,
        player = Player(
          playerName,
          Health(10),
          PlayerLevelMeta(Position(???, ???), TimeDelta(1d))
        ),
        timeLines = TimeLineStore(Map.empty),
        world = world
      )
    } yield game

  def saveGame(): F[Game]

  def loadGame(uuid: UUID): F[Game]
}
