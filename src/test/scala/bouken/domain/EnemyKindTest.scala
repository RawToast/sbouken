package bouken.domain

import cats.syntax.show._
import org.scalatest.{FreeSpec, Matchers}

class EnemyKindTest extends FreeSpec with Matchers {

  "EnemyKind" - {

    "show" - {
      "should correctly convert a zombie" in {
        val z: EnemyKind = Zombie
        z.show shouldBe "Z"
      }

      "should correctly convert a gnoll" in {
        val g: EnemyKind = Gnoll
        g.show shouldBe "X"
      }

      "should correctly convert a minotaur" in {
        val m: EnemyKind = Minotaur
        m.show shouldBe "M"
      }
    }
  }
}
