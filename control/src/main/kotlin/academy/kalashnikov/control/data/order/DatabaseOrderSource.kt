package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.data.item.ItemEntity
import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.domain.order.ClientOrder
import academy.kalashnikov.control.domain.order.OrderSource
import academy.kalashnikov.control.domain.order.Outcome
import academy.kalashnikov.control.domain.order.WarehouseOrder
import academy.kalashnikov.control.domain.status.OrderStatus
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID
import javax.inject.Inject

class DatabaseOrderSource @Inject constructor(
    private val application: Application,
    private val dispatchersProvider: DispatchersProvider
) : OrderSource {
    override suspend fun getClientOrders(): List<ClientOrder> = newSuspendedTransaction(dispatchersProvider.io) {
        ClientOrderEntity.all()
            .map { it.toOrder() }
    }

    override suspend fun getWarehouseOrders(): List<WarehouseOrder> = newSuspendedTransaction(dispatchersProvider.io) {
        WarehouseOrderEntity.find { OrderTable.statusId eq OrderStatus.WAREHOUSE.id }
            .map { it.toOrder() }
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
    }

    override suspend fun confirmClientOrder(orderId: String): Outcome = tryShiftingStatus(
        orderId,
        mapOf(OrderStatus.DELIVERED.id to OrderStatus.CLAIMED.id)
    )

    override suspend fun confirmWarehouseOrder(orderId: String): Outcome {
        val transitions = if (application.developmentMode) {
            OrderStatus.all
                .zipWithNext()
                .associate { it.first.id to it.second.id }
        } else {
            mapOf(OrderStatus.WAREHOUSE.id to OrderStatus.DELIVERING.id)
        }
        return tryShiftingStatus(orderId, transitions)
    }

    private suspend fun tryShiftingStatus(
        orderId: String,
        transitions: Map<String, String>
    ): Outcome = newSuspendedTransaction(dispatchersProvider.io) {
        val orderStatusId = OrderTable.select { OrderTable.id eq orderId }
            .singleOrNull()
            ?.get(OrderTable.statusId)
            ?.value
        if (orderStatusId == null) {
            Outcome.NOT_FOUND
        } else {
            val nextStatusId = transitions[orderStatusId]
            if (nextStatusId == null) {
                Outcome.CONFLICT
            } else {
                shiftStatus(orderId, nextStatusId)
            }
        }
    }

    private fun shiftStatus(orderId: String, nextStatusId: String): Outcome {
        OrderTable.update({ OrderTable.id eq orderId }) {
            it[statusId] = nextStatusId
        }
        return Outcome.SUCCESS
    }
}
