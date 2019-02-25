package bouken.world

import bouken.domain.{Area, Place, Position}

trait AreaParser[T[_]] {
  def parse(value: String): T[Area]
}

case class OptionAreaParser(placeParser: PlaceParser[Option]) extends AreaParser[Option] {
  def parse(value: String): Option[Area] = {

    val possiblePlaces: List[List[Option[Place]]] = value
      .trim
      .split("\n")
      .toList
      .map(_.split(",").toList)
      .map(_.map(_.trim))
      .map(_.map(placeParser.parse))
      .reverse

    val cells = if (possiblePlaces.exists(_.exists(_.isEmpty))) None
    else Some(possiblePlaces.map(_.flatten))

    cells.map(
      _.zipWithIndex.map {
        case (col, y) => col.zipWithIndex.map {
          case (p, x) => Position(x, y) -> p
        }
      }.foldLeft(Map.empty[Position, Place])(_ ++ _))
      .flatMap(c =>
        if (c.isEmpty) None
        else Some(Area(c))
      )

  }
}
