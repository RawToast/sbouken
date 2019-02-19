package bouken.server

import bouken.domain._
import bouken.server.Protocol.GameViewResponse.CurrentLevel
import cats.syntax.show._

object Converters {
  def areaToTiles(position: Position, place: Place): CurrentLevel.Tile = {
    CurrentLevel.Tile(position, place.show, None)
  }
}
