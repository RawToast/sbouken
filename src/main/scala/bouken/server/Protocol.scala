package bouken.server

import io.circe._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.extras.semiauto.deriveUnwrappedEncoder
import bouken.domain._
import enumeratum._

object Protocol {

  case class GameViewResponse(player: GameViewResponse.Player, currentLevel: GameViewResponse.CurrentLevel)

  object GameViewResponse {
    def apply(game: Game): Option[GameViewResponse] = {
      val player = Player(game.player.name, game.player.health, game.player.meta.timeDelta)

      for {
        level <- game.world.currentLevel
        area = level.area
        tiles = areaToTiles(area.value)
        currentLevel = CurrentLevel(game.world.current, tiles, Some(level.tileSet))
      } yield GameViewResponse(
        player,
        currentLevel
      )
    }

    private def areaToTiles(area: Map[Position, Place]): Set[CurrentLevel.Tile] =
      area
        .map{ case (position, place) => Converters.positionToTile(position, place) }
        .toSet


    case class Player(name: String, health: Health, timeDelta: TimeDelta)
    object Player {
      implicit private val healthEncoder: Encoder[Health] = deriveUnwrappedEncoder[Health]
      implicit private val deltaEncoder: Encoder[TimeDelta] = deriveUnwrappedEncoder[TimeDelta]
      implicit val encoder: Encoder[GameViewResponse.Player] = deriveEncoder[GameViewResponse.Player]
    }

    case class CurrentLevel(name: Level.Name, area: Set[CurrentLevel.Tile], tileSet: Option[Level.TileSet])
    object CurrentLevel {

      case class Tile(position: Position, value: String, description: Option[String])
      object Tile {
        case class Meta(tile: Tile, enemyKind: Option[EnemyKind], tileEffect: Option[TileEffect])

        sealed trait TileEffect extends EnumEntry
        case object TileEffect extends Enum[TileEffect] with CirceEnum[TileEffect] {
          def apply(eff: bouken.domain.TileEffect): Option[TileEffect] = eff match {
            case bouken.domain.NoEffect   => None
            case bouken.domain.Trap(_)    => Some(Trap)
            case bouken.domain.Snare(_)   => Some(Snare)
            case bouken.domain.Heal(_)    => Some(Heal)
            case bouken.domain.Gold(_)    => Some(Gold)
          }
          val values = findValues

          case object Trap extends TileEffect
          case object Snare extends TileEffect
          case object Heal extends TileEffect
          case object Gold extends TileEffect
        }

        implicit private val positionEncoder: Encoder[Position] = deriveEncoder[Position]
        implicit val tileEncoder: Encoder[Tile] = deriveEncoder[Tile]
      }

      implicit private val nameEncoder: Encoder[Level.Name] = deriveUnwrappedEncoder[Level.Name]
      implicit val encoder: Encoder[CurrentLevel] = deriveEncoder[CurrentLevel]
    }

    implicit val jsonEncoder: Encoder[GameViewResponse] = deriveEncoder[GameViewResponse]
  }

}
