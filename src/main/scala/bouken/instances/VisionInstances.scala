package bouken.instances

import bouken.{Position, Sight, Visibility}
import bouken.domain.Area

object VisionInstances {

  implicit val AreaVision: Visibility[Area] = new Visibility[Area] {

    override def updateVisibility[S: Sight](a: Area, s: S, position: Position): Area = {
//      val sight: Sight[S] = implicitly[Sight[S]]


      a
    }
  }
}
