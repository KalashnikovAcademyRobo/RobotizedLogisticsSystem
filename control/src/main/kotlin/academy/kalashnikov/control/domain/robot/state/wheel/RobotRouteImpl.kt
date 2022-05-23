package academy.kalashnikov.control.domain.robot.state.wheel

import academy.kalashnikov.control.domain.core.navigation.Direction
import academy.kalashnikov.control.domain.core.navigation.Graph.root
import javax.inject.Inject

class RobotRouteImpl @Inject constructor() : RobotRoute {
    override var node = root
    override var direction = Direction.BACK
}
