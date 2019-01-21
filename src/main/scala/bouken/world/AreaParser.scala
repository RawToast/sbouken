package bouken.world

import bouken.Position
import bouken.domain.{Area, Place}

case class AreaParser(placeParser: PlaceParser) {
  def parse(value: String): Area = {

    val cells: Map[Position, Place] = value
      .trim
      .split("\n")
      .toList
      .map(_.split(",").toList)
      .map(_.map(_.trim))
      .map(_.map(placeParser.parse))
      .zipWithIndex.map {case (col, y) =>
        col.zipWithIndex.map{case (p, x) => Position(x, y) -> p }}
        .foldLeft(Map.empty[Position, Place])(_++_)

    Area(cells)
  }
}
