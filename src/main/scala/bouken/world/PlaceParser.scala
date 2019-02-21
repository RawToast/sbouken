package bouken.world

import bouken.domain._
import scala.util.Try

import bouken.domain.EnemyKind._

trait PlaceParser[T[_]] {
  def parse(value: String): Option[Place]
}

object OptionPlaceParser extends PlaceParser[Option] {

  def parse(value: String): Option[Place] =
    if (value.exists(_ == '|')) handleComplexPlace(value)
    else parseTile(value).map(makeTile(_))

  private def handleComplexPlace(value: String): Option[Place] = {
    val tileStr = value.takeWhile(_ != '|')
    val otherStr = value.dropWhile(_ != '|').tail

    val tileOpt = parseTile(tileStr)

    val effect = otherStr match {
      case "," => Some(Trap(2))
      case ";" => Some(Snare(2))
      case "+" => Some(Heal(2))
      case "g" => Some(Gold(3))
      case _   => None
    }

    val enemy = otherStr match {
      case "Z" => Some(Enemy(Zombie))
      case "X" => Some(Enemy(Gnoll))
      case "M" => Some(Enemy(Minotaur))
      case _   => None
    }

    tileOpt.map(tile =>
      (effect, enemy) match {
        case (Some(e), _) => makeTile(tile, tileEffect = e)
        case (_, Some(e)) => makeTile(tile, state = e)
        case _ => makeTile(tile)
      }
    )
  }

  private def parseTile(string: String): Option[Tile] =
    string match {
      case "." => Some(Ground)
      case ":" => Some(Rough)
      case "#" => Some(Wall)
      case "w" => Some(Water)
      case e if isExit(e) => Some(Exit(Score(e.tail.toInt)))
      case s if isStairs(s) => Some(makeStairs(s))
      case _ => None
    }

  private def makeTile(
    tile: Tile = Ground,
    visible: Boolean = false,
    state: Occupier = Empty,
    tileEffect: TileEffect = NoEffect
  ) = Place(
    visible,
    tile,
    state,
    tileEffect
  )

  private def isStairs(string: String): Boolean =
    string.length >= 3 &&
      string.charAt(0) == '/' &&
      Try(string.charAt(1).toInt).toOption.isDefined

  private def isExit(string: String): Boolean =
    string.startsWith("e") &&
      Try(string.drop(1).toInt).toOption.isDefined

  private def makeStairs(string: String): Stairs = {
    val value = string.tail
    val id = value.takeWhile(x => Numbers.contains(x)).toInt
    val level = value.dropWhile(x => Numbers.contains(x))

    Stairs(To(id, level))
  }

  private val Numbers = "0123456789"
}
