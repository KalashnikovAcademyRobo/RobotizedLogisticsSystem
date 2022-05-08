package academy.kalashnikov.control.domain.order

interface OrderSource {
    suspend fun getClientOrders(): List<ClientOrder>
    suspend fun getWarehouseOrders(): List<WarehouseOrder>
    suspend fun createOrder(itemId: String): Outcome
    suspend fun confirmClientOrder(orderId: String): Outcome
    suspend fun confirmWarehouseOrder(orderId: String): Outcome
}
