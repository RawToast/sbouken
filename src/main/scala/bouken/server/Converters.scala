package bouken.server

import bouken.domain._
import bouken.server.Protocol.GameViewResponse.CurrentLevel
import bouken.server.Protocol.GameViewResponse.CurrentLevel.Tile

object Converters {
  def positionToTile(position: Position, place: Place): CurrentLevel.Tile = {
    CurrentLevel.Tile(position, Tile.Meta(place), None)
  }
}
