package academy.kalashnikov.control.domain.robot.state.wheel

import academy.kalashnikov.control.domain.core.navigation.Direction
import academy.kalashnikov.control.domain.core.navigation.Node

interface RobotRoute {
    var node: Node
    var direction: Direction
}
