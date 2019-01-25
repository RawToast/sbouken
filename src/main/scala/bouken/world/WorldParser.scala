package bouken.world

import bouken.domain.World

trait WorldParser[T[_]] {
  def parseWorld(directory: String): T[World]
}
