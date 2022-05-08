package academy.kalashnikov.control.domain.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseOrder(
    @SerialName("id")
    val id: String,

    @SerialName("itemId")
    val itemId: String
)
