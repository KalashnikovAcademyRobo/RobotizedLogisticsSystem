package academy.kalashnikov.control.domain.robot.state

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.status.OmniwheelRobotStatusTracker
import academy.kalashnikov.control.domain.status.WheelRobotStatusTracker
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ManipulatorRobotState @Inject constructor(
    private val wheelRobotStatus: WheelRobotStatusTracker,
    private val omniwheelRobotStatus: OmniwheelRobotStatusTracker,
    private val communication: Communication
) : RobotState {
    override suspend fun operate(): RobotState? {
        while (true) {
            val orderId = wheelRobotStatus.manipulatorOrderId
                .filterNotNull()
                .first()
            communication.send("pick")
            omniwheelRobotStatus.goingToManipulator.first { it.not() }
            communication.send("put")
            if (waitForFinished()) {
                return null
            }
            wheelRobotStatus.setManipulatorOrderId(null)
            omniwheelRobotStatus.setCarryingOrderId(orderId)
        }
    }

    private suspend fun waitForFinished(): Boolean {
        var command = communication.receive()
        while (command != "finished") {
            if (command == null) {
                return true
            }
            command = communication.receive()
        }
        return false
    }
}
