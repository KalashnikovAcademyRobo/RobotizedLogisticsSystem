package academy.kalashnikov.control.domain.order

import academy.kalashnikov.control.domain.status.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientOrder(
    @SerialName("id")
    val id: String,

    @SerialName("itemId")
    val itemId: String,

    @SerialName("status")
    val status: OrderStatus
)
