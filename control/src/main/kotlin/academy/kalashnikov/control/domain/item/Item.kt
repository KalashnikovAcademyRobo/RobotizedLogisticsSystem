package academy.kalashnikov.control.domain.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("quantity")
    val quantity: Int
)
