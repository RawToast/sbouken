package bouken.domain

import org.scalatest.{FreeSpec, Matchers}

class EnemyTest extends FreeSpec with Matchers {

  "Enemy" - {
    "Can construct a Zombie" in {
      val enemy = Enemy(EnemyKind.Zombie)
      enemy.kind shouldBe EnemyKind.Zombie
    }

    "Can construct a Gnoll" in {
      val enemy = Enemy(EnemyKind.Gnoll)
      enemy.kind shouldBe EnemyKind.Gnoll
    }

    "Can construct a Minotaur" in {
      val enemy = Enemy(EnemyKind.Minotaur)
      enemy.kind shouldBe EnemyKind.Minotaur
    }
  }
}
