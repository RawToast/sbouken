package bouken.world

import io.circe._
import io.circe.parser._
import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

import scala.io.Source
import scala.util.Try

class LevelParserTest  extends FreeSpec with Matchers {

  "AreaParser" - {
    "when given a file containing a level in the classic structure" - {

      val parser = FileBasedLevelParser("world", AreaParser(PlaceParser))
      val level = parser.parseLevel("Dungeon 1.csv")

      "Uses the filename as the level name" in {
        level.map(_.name) shouldBe Some("Dungeon 1")
      }

      "Uses the default tileset" in {
        level.map(_.tileSet) shouldBe Some(TileSet.Default)
      }

      "Parses the area map" in {
        level.exists(_.area.value.isEmpty) shouldBe false
      }
    }

    "when given a file containing a level a json format" - {

      val parser = FileBasedLevelParser("world", AreaParser(PlaceParser))
      val level = parser.parseLevel("OtherLevel.json")

      "Uses the filename as the level name" in {
        level.map(_.name) shouldBe Some("Swamp")
      }

      "Uses the default tileset" in {
        level.map(_.tileSet) shouldBe Some(TileSet.Default)
      }

      "Parses the area map" in {
        level.exists(_.area.value.isEmpty) shouldBe false
      }
    }
  }
}

case class FileBasedLevelParser(directory: String, areaParser: AreaParser) extends LevelParser[Option] {
  override def parseLevel(fileName: String): Option[Level] = {

    val (name, fileType) = fileName.span(_ != '.')

    if (fileType == ".csv") parseClassicLevel(directory, name)
    else parseJsonLevel(directory, name)
  }

  private def parseClassicLevel(directory: String, fileName: String): Option[Level] = {
      val text: Option[String] = Try {
        val bufferedSource = Source.fromResource(s"$directory/$fileName.csv")
        val text = bufferedSource.getLines().map(_.trim).mkString("\n")
        bufferedSource.close()
        text
      }.toOption

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

    val textOpt: Option[String] = Try {
      val bufferedSource = Source.fromResource(s"$directory/$fileName.json")
      val text = bufferedSource.getLines().map(_.trim).mkString("\n")
      bufferedSource.close()
      text
    }.toOption

    for {
      text: String <- textOpt
      json: Json <- parse(text).toOption
      level: Level <- json.as[Level].toOption
    } yield level
  }
}
