package bouken.server

import bouken.domain.{GameView, Level, Player, TimeLine}
import bouken.services.GameManager
import cats.effect._
import io.circe._
import io.circe.generic.semiauto._
import io.circe.generic.extras.semiauto.deriveUnwrappedEncoder
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._

case class Routes(gameManager: GameManager[IO]) {
  import Routes._
  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }

  val gameService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / id =>
      val gv = gameManager.loadGame(java.util.UUID.fromString(id))
        .map(g => GameView(g.uuid, g.player, ???, ???))

      Ok(gv)
    case POST -> Root / playerName =>
      val gv: IO[GameView] = gameManager.createGame(playerName, "world", java.util.UUID.randomUUID())
        .map(g => GameView(g.uuid, g.player, ???, ???))

      Created(gv)
  }
}
object Routes{
  // lots to go
  implicit def levelEncoder: Encoder[Level] = deriveEncoder[Level]
  implicit def timeLineEncoder: Encoder[TimeLine] = deriveUnwrappedEncoder[TimeLine]
  implicit def playerEncoder = deriveEncoder[Player]
  implicit def gameViewEncoder = deriveEncoder[GameView]
  implicit def gameViewIOEncoder: EntityEncoder[IO, GameView] = jsonEncoderOf[IO, GameView]
}
