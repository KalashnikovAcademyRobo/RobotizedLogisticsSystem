package academy.kalashnikov.control.domain.robot

interface RobotSource {
    suspend fun findRobotById(id: String): Robot?
    suspend fun upsertRobot(robot: Robot)
}
