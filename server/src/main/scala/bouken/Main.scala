package bouken

import bouken.services.GameManager
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.olegpy.meow.hierarchy.deriveMonadErrorFromThrowable
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._

object Main extends IOApp {

  private val gameManager = GameManager.inMemory[IO]
  private val routes = bouken.server.Routes(gameManager)

  private val httpApp: HttpApp[IO] = Router("/" -> routes.gameService).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
