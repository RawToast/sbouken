package bouken.world

import bouken.domain.{Level, TileSet}
import io.circe.{Decoder, Json}
import io.circe.parser.parse

import scala.io.Source
import scala.util.Try

trait LevelParser[T[_]] {
  def parseLevel(fileName: String): T[Level]
}

case class OptionLevelParser(directory: String, areaParser: AreaParser) extends LevelParser[Option] {
  override def parseLevel(fileName: String): Option[Level] = {
    val (name, fileType) = fileName.span(_ != '.')

    if (fileType == ".csv") parseClassicLevel(directory, name)
    else parseJsonLevel(directory, name)
  }

  private def readText(path: String) =
    Try {
      val bufferedSource = Source.fromResource(path)
      val text = bufferedSource.getLines().map(_.trim).mkString("\n")
      bufferedSource.close()
      text
    }.toOption

  private def parseClassicLevel(directory: String, fileName: String): Option[Level] = {
    val text = readText(s"$directory/$fileName.csv")

    text
      .map(areaParser.parse)
      .map(area =>
        Level(
          area = area,
          name = fileName,
          tileSet = TileSet.Default
        )
      )
  }

  private def parseJsonLevel(directory: String, fileName: String): Option[Level] = {
    implicit val levelDecoder: Decoder[Level] = io.circe.Decoder.instance { c =>
      for {
        name <- c.downField("name").as[String]
        areaStr <- c.downField("area").as[String]
        area = areaParser.parse(areaStr)
        tileset <- c.downField("tileset").as[TileSet]
      } yield Level(area, name, tileset)
    }

    val textOpt = readText(s"$directory/$fileName.json")

    for {
      text: String <- textOpt
      json: Json <- parse(text).toOption
      level: Level <- json.as[Level].toOption
    } yield level
  }
}
