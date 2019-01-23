package bouken.world

import bouken.domain.Level

trait LevelParser[T[_]] {
  def parseLevel(fileName: String): T[Level]
}
