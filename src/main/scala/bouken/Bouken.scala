package bouken

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSExportTopLevel("HelloWorld")
class Bouken {

  @JSExport
  def bestMove(area: List[List[Int]]) = 3

  @JSExport
  def sayLast(thing: JSThing) = thing.asJvm().last


  @ScalaJSDefined
  trait JSThing extends js.Object {
    val first: String
    val last: String
  }
  object JSThing {
    def toThing(jsThing: JSThing) =
      Thing(jsThing.first, jsThing.last)
    implicit class Ops(jSThing: JSThing) {
      def asJvm() = toThing(jSThing)
    }
  }

  case class Thing(first: String, last: String)
}

