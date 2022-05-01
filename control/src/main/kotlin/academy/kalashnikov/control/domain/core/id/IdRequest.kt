package academy.kalashnikov.control.domain.core.id

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdRequest(
    @SerialName("id")
    val id: String
)
