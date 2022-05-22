package academy.kalashnikov.control.domain.robot.state

interface RobotState {
    suspend fun operate(): RobotState?
}
