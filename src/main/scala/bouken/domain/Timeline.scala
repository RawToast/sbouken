package bouken.domain

// Turn timeline, used for ordering of turns
case class Positions(timeDelta: TimeDelta, location: bouken.Position)

case class TimeDelta(value: Double) extends AnyVal
