package bouken.world

import bouken.domain.Level
import io.circe.{Decoder, Json}
import io.circe.parser.parse

import scala.io.Source
import scala.util.Try

trait LevelParser[T[_]] {
  def parseLevel(directory: String, fileName: String): T[Level]
}

case class OptionLevelParser(areaParser: AreaParser) extends LevelParser[Option] {
  override def parseLevel(directory: String, fileName: String): Option[Level] = {
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
          name = Level.Name(fileName),
          tileSet = Level.TileSet.Default
        )
      )
  }

  private def parseJsonLevel(directory: String, fileName: String): Option[Level] = {
    implicit val levelDecoder: Decoder[Level] = io.circe.Decoder.instance { c =>
      for {
        nameStr <- c.downField("name").as[String]
        areaStr <- c.downField("area").as[String]
        tileset <- c.downField("tileset").as[Level.TileSet]
        area = areaParser.parse(areaStr)
        name = Level.Name(nameStr)
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
