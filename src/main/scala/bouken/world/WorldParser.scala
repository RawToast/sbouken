package bouken.world

import java.io.File

import bouken.domain.{Level, World}

trait WorldParser[T[_]] {
  def parseWorld(directory: String): T[World]
}

case class OptionWorldParser(areaParser: LevelParser[Option]) extends WorldParser[Option] {

  import OptionWorldParser._

  override def parseWorld(directory: String): Option[World] = {
    val parser = areaParser.parseLevel(directory, _)
    val levels = readFileNames(directory)
      .map(n => {
        println(n); n
      })
      .map(parser)
      .flatten
      .foldLeft(Map.empty[Level.Name, Level]) {
        case (map, level) => map + (level.name -> level)
      }

    if (levels.isEmpty || !levels.keys.exists(_ == InitialLevel)) None
    else Some(World(
      current = InitialLevel, // Good for now, rework later
      levels = levels
    )
    )
  }

  private def readFileNames(directory: String): List[String] = {
    val path = Option.apply(Thread.currentThread().getContextClassLoader.getResource(directory))
    val fileOpt = path.map(_.getPath).map(new File(_))

    fileOpt.map { file =>
      if (file.exists() && file.isDirectory) file.listFiles().toList.filter(_.isFile).map(_.getName)
      else List.empty
    } match {
      case Some(w) => w
      case _ => List.empty
    }
  }
}

object OptionWorldParser {
  private val InitialLevel = Level.Name("Dungeon 1")
}