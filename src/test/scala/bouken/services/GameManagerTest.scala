package bouken.services

import bouken.world.{OptionAreaParser, OptionLevelParser, OptionPlaceParser, OptionWorldParser}
import cats.implicits._
import org.scalatest.{FreeSpec, Matchers}

class GameManagerTest extends FreeSpec with Matchers {
  import GameManagerTest._

  "GameManager" - {
    "createGame" - {

      val gameManager = GameManager(worldParser)

      "when able to construct a world" - {
        val successfulResult =
          gameManager.createGame("test", "world")

        "when the world can be created creates a game" in {
          successfulResult.isRight shouldBe true
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
