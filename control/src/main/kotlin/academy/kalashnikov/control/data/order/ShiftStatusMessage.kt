package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.domain.order.Outcome
import kotlinx.coroutines.CompletableDeferred

data class ShiftStatusMessage(
    val orderId: String,
    val outcome: CompletableDeferred<Outcome>
)
