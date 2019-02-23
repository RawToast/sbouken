package bouken.server

import bouken.services.InMemoryGameManager
import bouken.world.{OptionAreaParser, OptionLevelParser, OptionPlaceParser, OptionWorldParser}
import cats.effect.IO
import io.circe.Json
import org.scalatest.{FreeSpec, Matchers}
import org.http4s._
import org.http4s.implicits._

class RoutesTest extends FreeSpec with Matchers {

  import RoutesTest._

  "GameService" - {
    implicit val decoder: EntityDecoder[IO, Json] = org.http4s.circe.jsonDecoder

    "Create Game Route" - {

      "When a valid request is sent" - {
        lazy val request: Request[IO] = Request[IO](method = Method.POST, uri = Uri.uri("/test"))
        lazy val responseIO: IO[Response[IO]] = routes.gameService.orNotFound.run(request)
        lazy val response: Response[IO] = responseIO.unsafeRunSync()

        "Responds with 201 Created" in {
          response.status.code shouldBe 201
        }

        "The response contains the correct player name" in {
          val jsonResponse = response.as[Json].unsafeRunSync()

          val name = jsonResponse.hcursor.downField("player").downField("name").as[String].toOption
          name shouldBe Some("test")
          //          println(Printer.noSpaces.copy(dropNullValues = true).pretty(jsonResponse))
        }
      }
    }

    "Fetch Game Route" - {

      "Given the game exists" - {
        lazy val createGameRequest: Request[IO] = Request[IO](method = Method.POST, uri = Uri.uri("/test"))
        def fetchRequest(id: String): Request[IO] = Request[IO](method = Method.GET, uri = Uri.unsafeFromString(s"/$id"))

        lazy val result = {
          val createdGame = routes.gameService.orNotFound.run(createGameRequest).unsafeRunSync().as[Json].unsafeRunSync()
          val id = createdGame.hcursor.downField("id").as[String].toOption.getOrElse("dave")
          routes.gameService.orNotFound.run(fetchRequest(id)).unsafeRunSync()
        }

        "Responds with 200 OK" in {
          result.status.code shouldBe 200
        }

        "The response contains the correct player name" in {
          implicit val decoder: EntityDecoder[IO, Json] = org.http4s.circe.jsonDecoder

          val jsonResponse = result.as[Json].unsafeRunSync()

          val name = jsonResponse.hcursor.downField("player").downField("name").as[String].toOption
          name shouldBe Some("test")
        }
      }
    }
  }
}

object RoutesTest {

  import com.olegpy.meow.hierarchy._

  private val areaParser = OptionAreaParser(OptionPlaceParser)
  private val levelParser = OptionLevelParser(areaParser)
  val worldParser = OptionWorldParser(levelParser)
  val gameManager: InMemoryGameManager[IO] = InMemoryGameManager[IO](worldParser)

  val routes: Routes = Routes(gameManager)
}
