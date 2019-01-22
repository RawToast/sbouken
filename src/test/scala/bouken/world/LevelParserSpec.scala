package bouken.world

import bouken.domain._
import org.scalatest.{FreeSpec, Matchers}

import scala.io.Source
import scala.util.Try

class LevelParserSpec  extends FreeSpec with Matchers {

  "AreaParser" - {
    "when given a file containing a level in the classic structure" - {

      val parser = FileBasedLevelParser("world", AreaParser(PlaceParser))
      val level = parser.parseLevel("Dungeon 1.csv")

      "Uses the filename as the level name" in {
        level.name shouldBe "Dungeon 1"
      }

      "Uses the default tileset" in {
        level.tileSet shouldBe Default
      }

      "Parses the area map" in {
        level.area.value.isEmpty shouldBe false
      }
    }
  }
}

case class FileBasedLevelParser(directory: String, areaParser: AreaParser) extends LevelParser {
  override def parseLevel(fileName: String): Level = {

    val (name, fileType) = fileName.span(_ != '.')

    if (fileType == ".csv") parseClassicLevel(directory, name)
    else parseJsonLevel(directory, name)
  }

  private def parseClassicLevel(directory: String, fileName: String): Level = {
      val text: Option[String] = Try {
        val bufferedSource = Source.fromResource(s"$directory/$fileName.csv")
        val text = bufferedSource.getLines().map(_.trim).mkString("\n")
        bufferedSource.close()
        text
      }.toOption

      val area =
        text.map(areaParser.parse)
          .getOrElse(Area(Map.empty))

      Level(
        area = area,
        name = fileName,
        tileSet = Default
      )
    }

  private def parseJsonLevel(directory: String, fileName: String): Level =
    Level(
      area = Area(Map.empty),
      name = fileName,
      tileSet = Default
    )
}
