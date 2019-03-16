package bouken.server

import java.util.UUID

import bouken.domain
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.generic.extras.semiauto.deriveUnwrappedEncoder
import bouken.domain.{Player => _, _}
import enumeratum._
import com.softwaremill.quicklens._

object Protocol {

  case class GameViewResponse(id: UUID, player: GameViewResponse.Player, level: GameViewResponse.CurrentLevel)

  object GameViewResponse {
    def apply(game: Game): Option[GameViewResponse] = {
      val player = Player(game.player.name, game.player.health, game.player.meta.timeDelta)

      for {
        level <- game.world.currentLevel
        area = level.area
        tiles = areaToTiles(area.value, game.player.meta.position)
        currentLevel = CurrentLevel(game.world.current, game.player.meta.position, tiles, Some(level.tileSet))
      } yield GameViewResponse(
        game.uuid,
        player,
        currentLevel
      )
    }

    private def areaToTiles(area: Map[Position, Place], playerPosition: Position): Set[CurrentLevel.Tile] =
      area
        .map{ case (position, place) =>
          if (position == playerPosition) Converters.positionToTile(position, place)
            .modify(_.meta.occupier).setTo(Some(CurrentLevel.Tile.Meta.Occupier.Player))
          else Converters.positionToTile(position, place)
        }
        .toSet


    case class Player(name: String, health: Health, timeDelta: TimeDelta)
    object Player {
      def fromPlace(place: bouken.domain.Place): Option[Player] = place.state match {
        case p: bouken.domain.Player => Some(Player(p.name, p.health, p.meta.timeDelta))
        case _ => None
      }

      implicit private val healthEncoder: Encoder[Health] = deriveUnwrappedEncoder[Health]
      implicit private val deltaEncoder: Encoder[TimeDelta] = deriveUnwrappedEncoder[TimeDelta]
      implicit val encoder: Encoder[GameViewResponse.Player] = deriveEncoder[GameViewResponse.Player]
    }

    case class CurrentLevel(
      name: Level.Name,
      playerLocation: Position,
      area: Set[CurrentLevel.Tile],
      tileSet: Option[Level.TileSet]
    )
    object CurrentLevel {

      implicit val positionEncoder: Encoder[Position] = deriveEncoder[Position]

      case class Tile(position: Position, meta: Tile.Meta, description: Option[String])
      object Tile {
        case class Meta(
          tile: TileKind,
          visibility: Meta.Visibility,
          occupier: Option[Meta.Occupier],
          tileEffect: Option[TileEffect]
        )
        object Meta {
          def apply(place: Place): Meta = Meta(
            TileKind(place.tile),
            Visibility(7),
            Occupier.fromPlace(place),
            TileEffect(place.tileEffect)
          )

          sealed trait Occupier
          object Occupier {
            import cats.syntax.show._
            case object Player extends Occupier
            case class Enemy(name: String, description: String) extends Occupier

            def fromPlace(p: Place): Option[Occupier] = {
              p.state match {
                case Empty => None
                case domain.Player(_, _, _)  => Some(Occupier.Player)
                case domain.Enemy(kind, _) => Some(Occupier.Enemy(kind.show, ""))
              }
            }

            implicit val encoder: Encoder[Occupier] = Encoder.instance {
              case Player => "Player".asJson
              case Enemy(name, description)       => Json.obj(
                "name" -> name.asJson,
                "description" -> description.asJson)
            }

          }

          case class Visibility(value: Int) extends AnyVal

          case object Visibility {
            implicit val encoder: Encoder[Visibility] = deriveUnwrappedEncoder
          }

          implicit val encoder: Encoder[Meta] = deriveEncoder
        }

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

        sealed trait TileKind extends EnumEntry
        case object TileKind extends Enum[TileKind] with CirceEnum[TileKind] {
          def apply(eff: bouken.domain.TileType): TileKind = eff match {
            case bouken.domain.Ground => Ground
            case bouken.domain.Rough  => Rough
            case bouken.domain.Water  => Water
            case bouken.domain.Wall   => Wall
            case bouken.domain.Stairs(_) => Stairs
            case bouken.domain.Exit(_)   => Exit
          }
          val values = findValues

          case object Blank extends TileKind
          case object Ground extends TileKind
          case object Rough extends TileKind
          case object Water extends TileKind
          case object Wall extends TileKind
          case object Stairs extends TileKind
          case object Exit extends TileKind
        }

        implicit val tileEncoder: Encoder[Tile] = deriveEncoder[Tile]
      }

      implicit private val nameEncoder: Encoder[Level.Name] = deriveUnwrappedEncoder[Level.Name]
      implicit val encoder: Encoder[CurrentLevel] = deriveEncoder[CurrentLevel]
    }

    implicit val jsonEncoder: Encoder[GameViewResponse] = deriveEncoder[GameViewResponse]
  }

}
