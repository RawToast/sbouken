package bouken.services

import java.util.UUID

import bouken.domain.Game
import bouken.services.ManagementError.{FailedToCreateGame, GameDoesNotExist}
import bouken.world.{OptionAreaParser, OptionLevelParser, OptionPlaceParser, OptionWorldParser}
import cats.effect.IO
import cats.implicits._
import org.scalatest.{FreeSpec, Matchers}

class GameManagerTest extends FreeSpec with Matchers {
  import GameManagerTest._

  "GameManager" - {
    "createGame" - {
      val gameManager = InMemoryGameManager(worldParser)
      val testUUID = UUID.randomUUID()

      "when able to construct a world" - {
        val successfulResult =
          gameManager.createGame("test", "world", testUUID)

        "when the world can be created creates a game" in {
          successfulResult shouldBe a[Right[_, Game]]
        }
      }

      "when the game directory is invalid" - {
        val successfulResult =
          gameManager.createGame("test", "abc", testUUID)

        "fails to create a game" in {
          successfulResult shouldBe Left(FailedToCreateGame)
        }
      }
    }

    "loadGame" - {
      val testUUID = UUID.randomUUID()

      "when the game exists" - {
        val gameManager = InMemoryGameManager(worldParser)

        val result = for {
          g    <- gameManager.createGame("test", "world", testUUID)
          _    <- gameManager.saveGame(g)
          game <- gameManager.loadGame(testUUID)
        } yield game

        "returns successfully" in {
          result shouldBe a[Right[_, Game]]
        }

        "returns the expected game" in {
          result.map(_.uuid) shouldBe Right(testUUID)
        }
      }

      "when the game does not exist" - {
        val gameManager = InMemoryGameManager(worldParser)
        val result = gameManager.loadGame(testUUID)

        "fails to load the game" in {
          result shouldBe Left(GameDoesNotExist)
        }
      }
    }
  }

  "IO GameManager" - {
    "createGame" - {
      import com.olegpy.meow.hierarchy.deriveMonadErrorFromThrowable
      val gameManager = InMemoryGameManager[IO](worldParser)
      val testUUID = UUID.randomUUID()

      "when able to construct a world" - {
        val successfulResult: Game =
          gameManager.createGame("test", "world", testUUID).unsafeRunSync()

        "when the world can be created creates a game" in {
          successfulResult shouldBe a[Game]
        }
      }

      "when the game directory is invalid" - {
        val successfulResult =
          gameManager.createGame("test", "abc", testUUID).attempt.unsafeRunSync()

        "fails to create a game" in {
          successfulResult shouldBe Left(FailedToCreateGame)
        }
      }
    }
  }
}

object GameManagerTest {
  private val areaParser = OptionAreaParser(OptionPlaceParser)
  private val levelParser = OptionLevelParser(areaParser)
  val worldParser = OptionWorldParser(levelParser)
}
