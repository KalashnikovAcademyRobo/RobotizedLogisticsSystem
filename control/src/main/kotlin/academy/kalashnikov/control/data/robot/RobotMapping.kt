package academy.kalashnikov.control.data.robot

import academy.kalashnikov.control.domain.robot.Robot

fun RobotEntity.toRobot() = Robot(
    id = id.value,
    type = type
)
