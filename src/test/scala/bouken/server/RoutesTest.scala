package bouken.server

import bouken.services.{GameManager, InMemoryGameManager, ManagementError, ManagementMonadError}
import bouken.world.{OptionAreaParser, OptionLevelParser, OptionPlaceParser, OptionWorldParser}
import cats.MonadError
import cats.effect.IO
import org.scalatest.{FreeSpec, Matchers}
import cats.implicits._
import org.http4s._
import org.http4s.implicits._

class RoutesTest extends FreeSpec with Matchers {
  import RoutesTest._

  "GameService" - {

    "Create Game Route" - {

      "When a valid request is sent" - {
          lazy val request: Request[IO] = Request[IO](method = Method.POST, uri = Uri.uri("/test"))
          lazy val responseIO: IO[Option[Response[IO]]] = routes.gameService.run(request).value
          lazy val response: Option[Response[IO]] = responseIO.unsafeRunSync()

        "Responds with 201 Created" in {
          response.map(_.status.code) shouldBe Some(201)
        }
      }
    }
  }
}
object RoutesTest{
  import cats.effect.implicits._
  import com.olegpy.meow.prelude._

  implicit val myError: ManagementMonadError[IO] = implicitly[MonadError[IO, ManagementError]]

  private val areaParser = OptionAreaParser(OptionPlaceParser)
  private val levelParser = OptionLevelParser(areaParser)
  val worldParser = OptionWorldParser(levelParser)
  val gameManager: InMemoryGameManager[IO] = InMemoryGameManager[IO](worldParser)

  val routes: Routes = Routes(gameManager)
}
