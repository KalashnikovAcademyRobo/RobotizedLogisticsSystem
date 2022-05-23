package academy.kalashnikov.control.domain.status

import kotlinx.coroutines.flow.Flow

interface WheelRobotStatusTracker {
    val manipulatorOrderId: Flow<String?>
    val carryingOrderId: Flow<String?>

    suspend fun setCarryingOrderId(orderId: String?)
    suspend fun setManipulatorOrderId(orderId: String?)
}
