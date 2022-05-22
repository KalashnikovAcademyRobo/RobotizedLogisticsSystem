package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.data.item.ItemEntity
import academy.kalashnikov.control.data.status.OrderStatusTable
import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.domain.order.ClientOrder
import academy.kalashnikov.control.domain.order.OrderSource
import academy.kalashnikov.control.domain.order.Outcome
import academy.kalashnikov.control.domain.order.WarehouseOrder
import academy.kalashnikov.control.domain.status.OmniwheelRobotStatusTracker
import academy.kalashnikov.control.domain.status.OrderStatus
import academy.kalashnikov.control.domain.status.WheelRobotStatusTracker
import io.ktor.server.application.Application
import kotlinx.coroutines.flow.MutableSharedFlow
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import javax.inject.Inject

class DatabaseOrderSource @Inject constructor(
    private val application: Application,
    private val dispatchersProvider: DispatchersProvider,
    private val wheelRobotStatusTracker: WheelRobotStatusTracker,
    private val omniwheelRobotStatusTracker: OmniwheelRobotStatusTracker
) : OrderSource {
    override val newOrders = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    override suspend fun getClientOrders(): List<ClientOrder> = newSuspendedTransaction(dispatchersProvider.io) {
        ClientOrderEntity.all()
            .map { it.toOrder() }
    }

    override suspend fun getWarehouseOrders(): List<WarehouseOrder> = newSuspendedTransaction(dispatchersProvider.io) {
        WarehouseOrderEntity.find { OrderTable.statusId eq OrderStatus.WAREHOUSE.id }
            .map { it.toOrder() }
    }

    override suspend fun getOrderForProcessing(): WarehouseOrder? = newSuspendedTransaction(dispatchersProvider.io) {
        WarehouseOrderEntity.find { OrderTable.statusId eq OrderStatus.PROCESSING.id }
            .firstOrNull()
            ?.toOrder()
    }

    override suspend fun createOrder(itemId: String) = newSuspendedTransaction(dispatchersProvider.io) {
        val item = ItemEntity.findById(itemId)
        when {
            item == null -> Outcome.NOT_FOUND
            item.quantity <= 0 -> Outcome.CONFLICT
            else -> {
                OrderTable.insert {
                    it[id] = UUID.randomUUID().toString()
                    it[this.itemId] = itemId
                    it[statusId] = OrderStatus.PROCESSING.id
                }
                item.quantity -= 1
                Outcome.SUCCESS
            }
        }
    }.also {
        if (it == Outcome.SUCCESS) {
            newOrders.emit(Unit)
        }
    }

    override suspend fun confirmClientOrder(orderId: String): Outcome = tryShiftingStatus(
        orderId,
        mapOf(OrderStatus.DELIVERED.id to OrderStatus.CLAIMED.id)
    ).also {
        if (it == Outcome.SUCCESS) {
            omniwheelRobotStatusTracker.goToManipulator()
        }
    }

    override suspend fun confirmWarehouseOrder(orderId: String): Outcome = if (application.developmentMode) {
        shiftOrderStatus(orderId)
    } else {
        tryShiftingStatus(orderId, mapOf(OrderStatus.WAREHOUSE.id to OrderStatus.DELIVERING.id))
    }.also {
        if (it == Outcome.SUCCESS) {
            wheelRobotStatusTracker.setCarryingOrderId(orderId)
        }
    }

    override suspend fun shiftOrderStatus(orderId: String): Outcome {
        val transitions = OrderStatus.all
            .zipWithNext()
            .associate { it.first.id to it.second.id }
        return tryShiftingStatus(orderId, transitions)
    }

    private suspend fun tryShiftingStatus(
        orderId: String,
        transitions: Map<String, String>
    ): Outcome = newSuspendedTransaction(dispatchersProvider.io) {
        val order = WarehouseOrderEntity.findById(orderId)
        if (order == null) {
            Outcome.NOT_FOUND
        } else {
            val nextStatusId = transitions[order.statusId.value]
            if (nextStatusId == null) {
                Outcome.CONFLICT
            } else {
                order.statusId = EntityID(nextStatusId, OrderStatusTable)
                Outcome.SUCCESS
            }
        }
    }
}
