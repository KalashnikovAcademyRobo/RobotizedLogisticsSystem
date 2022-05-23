package academy.kalashnikov.control.domain.status

import academy.kalashnikov.control.domain.order.OrderSource
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Provider

class OmniwheelRobotStatusTrackerImpl @Inject constructor(
    orderSourceProvider: Provider<OrderSource>
) : OmniwheelRobotStatusTracker {
    override val goingToManipulator = MutableSharedFlow<Boolean>(replay = 1)
    override val carryingOrderId = MutableSharedFlow<String?>(replay = 1)

    private val orderSource by lazy(orderSourceProvider::get)

    override suspend fun setCarryingOrderId(orderId: String?) {
        if (orderId == null) {
            carryingOrderId.replayCache.firstOrNull()
                ?.let { orderSource.shiftOrderStatus(it) }
        }
        carryingOrderId.emit(orderId)
    }

    override suspend fun goToManipulator() {
        goingToManipulator.emit(true)
    }

    override suspend fun waitForManipulator() {
        goingToManipulator.emit(false)
    }
}
