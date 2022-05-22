package academy.kalashnikov.control.domain.robot.state.wheel

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.core.navigation.Destination
import academy.kalashnikov.control.domain.order.OrderSource
import academy.kalashnikov.control.domain.robot.state.RobotState
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import javax.inject.Inject

class WheelProcessingState @Inject constructor(
    private val communication: Communication,
    private val orderSource: OrderSource,
    private val wheelPickingStateFactory: WheelPickingState.Factory
) : RobotState {
    override suspend fun operate(): RobotState? = coroutineScope {
        val processingJob = launch { processOrder() }
        select<Unit> {
            processingJob.onJoin {}
            communication.job.onJoin {}
        }
        if (processingJob.isActive) {
            processingJob.cancelAndJoin()
            null
        } else {
            wheelPickingStateFactory.create(Destination.USER)
        }
    }

    private suspend fun processOrder() {
        orderSource.newOrders
            .takeWhile {
                orderSource.getOrderForProcessing()
                    ?.let {
                        orderSource.shiftOrderStatus(it.id)
                    } == null
            }
            .collect()
    }
}
