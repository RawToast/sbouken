package bouken.world

import bouken.domain.Level

trait LevelParser {
  def parseLevel(fileName: String): Level
}
