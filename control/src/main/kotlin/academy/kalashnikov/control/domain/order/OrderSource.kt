package academy.kalashnikov.control.domain.order

import kotlinx.coroutines.flow.Flow

interface OrderSource {
    val newOrders: Flow<Unit>

    suspend fun getClientOrders(): List<ClientOrder>
    suspend fun getWarehouseOrders(): List<WarehouseOrder>
    suspend fun getOrderForProcessing(): WarehouseOrder?
    suspend fun createOrder(itemId: String): Outcome
    suspend fun confirmClientOrder(orderId: String): Outcome
    suspend fun confirmWarehouseOrder(orderId: String): Outcome
    suspend fun shiftOrderStatus(orderId: String): Outcome
}
