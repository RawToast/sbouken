package bouken.services

import java.util.UUID

import bouken.domain._
import bouken.world.WorldParser
import cats.{Monad, MonadError}

abstract class GameManager[F[_] : Monad](worldParser: WorldParser[Option]) {
  def createGame(playerName: String)(
    implicit F:
    MonadError[F, GameManagementError]
  ): F[Game]

  def saveGame(): F[Game]

  def loadGame(uuid: UUID): F[Game]
}
