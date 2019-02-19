package bouken.server

import bouken.domain._
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
      val response = gameManager.loadGame(java.util.UUID.fromString(id))
        .map(g => GameView(g.uuid, g.player, ???, ???))
        .map(GameViewResponse.apply)

      Ok(response)
    case POST -> Root / playerName =>
      val response: IO[GameViewResponse] = gameManager.createGame(playerName, "world", java.util.UUID.randomUUID())
        .map(g => GameView(g.uuid, g.player, ???, ???))
        .map(GameViewResponse.apply)

      Created(response)
  }
}
