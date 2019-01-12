package bouken.instances

import bouken.{Position, Sight, Vision}
import bouken.domain.Area

object VisionInstances {

  implicit val AreaVision: Vision[Area] = new Vision[Area] {

    override def updateVisibility[S: Sight](a: Area, s: S, position: Position): Area = {
      val sight: Sight[S] = implicitly[Sight[S]]


      ???
    }
  }
}
