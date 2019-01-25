package bouken.world

import java.io.File

import bouken.domain.{Level, World}
import org.scalatest.{FreeSpec, Matchers}

class WorldParserTest extends FreeSpec with Matchers {

  "WorldParser" - {

    val worldParser = OptionWorldParser(OptionLevelParser(AreaParser(PlaceParser)))

    "when given a directory containing a valid set of levels" - {

      val result = worldParser.parseWorld("world")


      "creates a world" in {
        result shouldBe a[Some[_]]
      }
    }

    "when given a directory that does not exist" - {

      val result = worldParser.parseWorld("abc123")

      "creates nothing" in {
        result shouldBe None
      }
    }
  }

}


case class OptionWorldParser(areaParser: LevelParser[Option]) extends WorldParser[Option] {
  override def parseWorld(directory: String): Option[World] = {
    val parser = areaParser.parseLevel(directory, _)
    val levels = readFileNames(directory)
      .map(n => {println(n); n})
      .map(parser)
      .flatten
      .foldLeft(Map.empty[Level.Name, Level]) {
        case (map, level) => map + (level.name -> level)
      }

    if (levels.isEmpty) None
    else Some(World(
      current = Level.Name(""), // How do? take as arg?
      levels = levels
      )
    )
  }

  private def readFileNames(directory: String): List[String] = {
    val path = Option.apply(Thread.currentThread().getContextClassLoader.getResource(directory))
    val fileOpt = path.map(_.getPath).map(new File(_))

    fileOpt.map{ file =>
      if (file.exists() && file.isDirectory) file.listFiles().toList.filter(_.isFile).map(_.getName)
      else List.empty
    } match {
      case Some(w) => w
      case _ => List.empty
    }
  }
}