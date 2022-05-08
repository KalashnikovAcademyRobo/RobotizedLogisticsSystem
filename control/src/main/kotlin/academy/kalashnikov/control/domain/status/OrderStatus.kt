package academy.kalashnikov.control.domain.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatus(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String
) {
    companion object {
        val PROCESSING = OrderStatus(
            id = "processing",
            name = "В обработке"
        )
        val WAREHOUSE = OrderStatus(
            id = "warehouse",
            name = "На складе"
        )
        val DELIVERING = OrderStatus(
            id = "delivering",
            name = "Доставляется"
        )
        val DELIVERED = OrderStatus(
            id = "delivered",
            name = "Доставлен"
        )
        val CLAIMED = OrderStatus(
            id = "claimed",
            name = "Получен"
        )
        val all = listOf(PROCESSING, WAREHOUSE, DELIVERING, DELIVERED, CLAIMED)
    }
}
