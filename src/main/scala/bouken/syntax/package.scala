package bouken

import bouken.types._

package object syntax {
  trait pathing extends LocatePosition.Ops with Navigation.ToNavigationOps with Pathing.ToPathingOps
  trait vision extends Visibility.ToVisibilityOps with Sight.Ops

  object pathing extends pathing
  object vision extends vision
  object all extends pathing with vision
}
