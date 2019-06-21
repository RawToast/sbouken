package bouken.services

import java.util.UUID

import cats.Monad
import cats.syntax.applicative._
import cats.syntax.flatMap._
import cats.syntax.functor._

case class ActionService[F[_]: Monad](
     gameManager: GameManager[F],
     playerMovement: PlayerMovement[F]
     ) {


  def use(move: PlayerMovement.Move, uuid: UUID) =

    for {
      game <- gameManager.loadGame(uuid)
      _ <- playerMovement.takeInput()

    } yield ???

}
