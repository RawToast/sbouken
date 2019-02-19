package bouken.server

import bouken.domain._
import bouken.services.GameManager
import cats.effect._
import io.circe._
import io.circe.syntax._
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
      val _ = gameManager.loadGame(java.util.UUID.fromString(id))
        .map(g => GameView(g.uuid, g.player, ???, ???))

      Ok()
    case POST -> Root / playerName =>
      val _: IO[GameView] = gameManager.createGame(playerName, "world", java.util.UUID.randomUUID())
        .map(g => GameView(g.uuid, g.player, ???, ???))

      Created()
  }
}
object Routes{
  // These encoders should be replaced with api objects that are more restricted.
//  implicit def healthEncoder: Encoder[Health] = deriveUnwrappedEncoder[Health]
//  implicit def positionEncoder: Encoder[Position] = deriveEncoder[Position]
//  implicit def timedeltaEncoder: Encoder[TimeDelta] = deriveUnwrappedEncoder[TimeDelta]
//  implicit def scoreEncoder: Encoder[Score] = deriveUnwrappedEncoder[Score]
//  implicit def toEncoder: Encoder[To] = deriveEncoder[To]
//
//  implicit def tileEffectEncoder: Encoder[TileEffect] = ???
//
//
//  // May want a better structure for this, probably worth referring to bouken
//  implicit def tileEncoder: Encoder[Tile] = Encoder.instance[Tile] {
//    case Ground    => "Ground".asJson
//    case Rough     => "Rough".asJson
//    case Water     => "Water".asJson
//    case Wall      => "Wall".asJson
//    case s: Stairs => deriveEncoder[Stairs].apply(s)
//    case e: Exit   => deriveEncoder[Exit].apply(e)
//  }
//
//  implicit def playerLevelMetaEncoder: Encoder[PlayerLevelMeta] = deriveEncoder[PlayerLevelMeta]
//  implicit def enemyKindEncoder: Encoder[EnemyKind] = deriveEncoder[EnemyKind]
//  implicit def occupierEncoder: Encoder[Occupier] = deriveEncoder[Occupier]
//
//  implicit def placeEncoder: Encoder[Place] = deriveEncoder[Place]
//
//  implicit def areaEncoder: Encoder[Area] = deriveUnwrappedEncoder[Area]
//  implicit def levelNameEncoder: Encoder[Level.Name] = deriveUnwrappedEncoder[Level.Name]
//  implicit def levelEncoder: Encoder[Level] = deriveEncoder[Level]
//
//  implicit def actorEncoder: Encoder[Actor] = deriveEncoder[Actor]
//  implicit def timeLineEncoder: Encoder[TimeLine] = deriveEncoder[TimeLine]
//
//  implicit def playerMetaEncoder = deriveEncoder[PlayerLevelMeta]
//  implicit def playerEncoder = deriveEncoder[Player]
//
//  implicit def gameViewEncoder = deriveEncoder[GameView]
//  implicit def gameViewIOEncoder: EntityEncoder[IO, GameView] = jsonEncoderOf[IO, GameView]
}
