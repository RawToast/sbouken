package bouken.domain

import java.util.UUID

case class Game(uuid: UUID, player: Player, timeLines: TimeLineStore, world: World)

case class GameView(uuid: UUID, player: Player, timeLine: TimeLine, level: Level)