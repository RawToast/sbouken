package bouken.server

import bouken.server.Protocol.GameViewResponse
import bouken.services.GameManager
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._

case class Routes(gameManager: GameManager[IO]) {

  implicit val entityEncoder: EntityEncoder[IO, GameViewResponse] = jsonEncoderOf[IO, GameViewResponse]

  val gameService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / id =>
      gameManager
        .loadGame(java.util.UUID.fromString(id))
        .map(GameViewResponse.apply)
        .flatMap {
          case Some(value) => Ok(value)
          case None        => InternalServerError()
        }

    case POST -> Root / playerName =>
      (for {
        game <- gameManager.createGame(playerName, "world", java.util.UUID.randomUUID())
        _    <- gameManager.saveGame(game)
        maybeResponse  = GameViewResponse.apply(game)
      } yield maybeResponse)
        .flatMap {
          case Some(value) => Created(value)
          case None        => InternalServerError()
        }
  }
}
