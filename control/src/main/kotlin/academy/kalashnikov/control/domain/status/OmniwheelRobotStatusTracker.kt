package academy.kalashnikov.control.domain.status

import kotlinx.coroutines.flow.Flow

interface OmniwheelRobotStatusTracker {
    val goingToManipulator: Flow<Boolean>
    val carryingOrderId: Flow<String?>

    suspend fun setCarryingOrderId(orderId: String?)
    suspend fun goToManipulator()
    suspend fun waitForManipulator()
}
