package academy.kalashnikov.control.domain.status

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class WheelRobotStatusTrackerImpl @Inject constructor(
) : WheelRobotStatusTracker {
    override val manipulatorOrderId = MutableSharedFlow<String?>(replay = 1)
    override val carryingOrderId = MutableSharedFlow<String?>(replay = 1)

    override suspend fun setCarryingOrderId(orderId: String?) {
        carryingOrderId.emit(orderId)
    }

    override suspend fun setManipulatorOrderId(orderId: String?) {
        manipulatorOrderId.emit(orderId)
    }
}
