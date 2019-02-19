package bouken.server

import io.circe._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.extras.semiauto.deriveUnwrappedEncoder
import bouken.domain.{Health, Level, Position, TimeDelta}
import bouken.server.Protocol.GameView.CurrentLevel.PositionedTile

object Protocol {

  case class GameView(player: GameView.Player, currentLevel: GameView.CurrentLevel)

  object GameView {
    case class Player(name: String, health: Health, timeDelta: TimeDelta)
    object Player {
      implicit private val healthEncoder: Encoder[Health] = deriveUnwrappedEncoder[Health]
      implicit private val deltaEncoder: Encoder[TimeDelta] = deriveUnwrappedEncoder[TimeDelta]
      implicit val encoder: Encoder[GameView.Player] = deriveEncoder[GameView.Player]
    }

    case class CurrentLevel(name: Level.Name, area: Set[PositionedTile], tileSet: Level.TileSet)
    object CurrentLevel {
      case class PositionedTile(position: Position, tile: Tile)
      case class Tile(value: String, description: Option[String])

      implicit private val nameEncoder: Encoder[Level.Name] = deriveUnwrappedEncoder[Level.Name]
      implicit private val positionEncoder: Encoder[Position] = deriveEncoder[Position]
      implicit private val tileEncoder: Encoder[Tile] = deriveEncoder[Tile]
      implicit private val positionedTileEncoder: Encoder[PositionedTile] = deriveEncoder[PositionedTile]

      implicit val encoder: Encoder[CurrentLevel] = deriveEncoder[CurrentLevel]
    }
  }
}
