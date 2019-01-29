package bouken.world

import bouken.domain.Level
import io.circe.parser.parse
import io.circe.{Decoder, Json}

import scala.io.Source
import scala.util.Try

trait LevelParser[T[_]] {
  def parseLevel(directory: String, fileName: String): T[Level]
}

case class OptionLevelParser(areaParser: AreaParser[Option]) extends LevelParser[Option] {
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
      .flatMap(areaParser.parse)
      .map(area =>
        Level(
          area = area,
          name = Level.Name(fileName),
          tileSet = Level.TileSet.Default
        )
      )
  }

  private def parseJsonLevel(directory: String, fileName: String): Option[Level] = {
    implicit val levelDecoder: Decoder[(String, Level.Name, Level.TileSet)] = io.circe.Decoder.instance { c =>
      for {
        nameStr <- c.downField("name").as[String]
        areaStr <- c.downField("area").as[String]
        tileset <- c.downField("tileset").as[Level.TileSet]
        name = Level.Name(nameStr)
      } yield (areaStr, name, tileset)
    }

    val textOpt = readText(s"$directory/$fileName.json")

    for {
      text: String <- textOpt
      json: Json <- parse(text).toOption
      (str, name, set) <- json.as[(String, Level.Name, Level.TileSet)].toOption
      area <- areaParser.parse(str)
    } yield Level(area, name, set)
  }
}
