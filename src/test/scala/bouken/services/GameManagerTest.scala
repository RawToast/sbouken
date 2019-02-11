package bouken.services

import java.util.UUID

import bouken.domain.Game
import bouken.services.ManagementError.FailedToCreateGame
import bouken.world.{OptionAreaParser, OptionLevelParser, OptionPlaceParser, OptionWorldParser}
import cats.implicits._
import org.scalatest.{FreeSpec, Matchers}

class GameManagerTest extends FreeSpec with Matchers {
  import GameManagerTest._

  "GameManager" - {
    "createGame" - {

      val gameManager = GameManager(worldParser)
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
  }
}

object GameManagerTest {
  private val areaParser = OptionAreaParser(OptionPlaceParser)
  private val levelParser = OptionLevelParser(areaParser)
  val worldParser = OptionWorldParser(levelParser)
}
