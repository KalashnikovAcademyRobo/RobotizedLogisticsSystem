package academy.kalashnikov.control.data.robot

import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.domain.robot.Robot
import academy.kalashnikov.control.domain.robot.RobotSource
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.inject.Inject

class RobotDatabaseSource @Inject constructor(
    private val dispatchersProvider: DispatchersProvider
) : RobotSource {
    override suspend fun findRobotById(id: String): Robot? = newSuspendedTransaction(dispatchersProvider.io) {
        RobotEntity.findById(id)
            ?.toRobot()
    }

    override suspend fun upsertRobot(robot: Robot) {
        newSuspendedTransaction(dispatchersProvider.io) {
            RobotTable.deleteWhere { RobotTable.id eq robot.id }
            RobotEntity.new(robot.id) {
                type = robot.type
            }
        }
    }
}
