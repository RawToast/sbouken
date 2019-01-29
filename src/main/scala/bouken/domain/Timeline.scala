package bouken.domain

// Turn timeline, used for ordering of turns
case class TimeLineStore(timeLines: Map[Level.Name, TimeLine])

case class TimeLine(positions: Map[Position, Actor])

case class Actor(timeDelta: Double, isActive: Boolean)

case class TimeDelta(value: Double) extends AnyVal
