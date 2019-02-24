package bouken

import cats.effect.{ExitCode, IO}
import org.scalatest.{FreeSpec, Matchers}
import cats.effect._
import org.http4s._

import scala.concurrent.ExecutionContext
import java.util.concurrent._

import io.circe.Json
import org.http4s.client.{Client, JavaNetClientBuilder, UnexpectedStatus}

import scala.concurrent.ExecutionContext.global

class ServerTest extends FreeSpec with Matchers {

  import ServerTest._

  "GameServer" - {
    implicit val decoder: EntityDecoder[IO, Json] = org.http4s.circe.jsonDecoder
    implicit val timer: Timer[IO] = IO.timer(global)

    val fibre: Fiber[IO, ExitCode] = server.start.unsafeRunSync()

    "Returns NotFound for invalid requests" in {
      val request = Request[IO](method = Method.DELETE, uri = Uri.unsafeFromString("http://localhost:8088/dave"))
      lazy val response: Either[Throwable, Json] = httpClient.expect[Json](request).attempt.unsafeRunSync()

      response shouldBe Left(UnexpectedStatus(Status.NotFound))
    }
    "Responds successfully to valid requests" in {
      val request = Request[IO](method = Method.POST, uri = Uri.unsafeFromString("http://localhost:8088/dave"))
      lazy val response: Either[Throwable, Json] = httpClient.expect[Json](request).attempt.unsafeRunSync()

      response shouldBe a[Right[_, Json]]
    }
  }
}

object ServerTest {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  val server: IO[ExitCode] = Main.run(List.empty)

  private val blockingEC = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))
  val httpClient: Client[IO] = JavaNetClientBuilder[IO](blockingEC).create
}
