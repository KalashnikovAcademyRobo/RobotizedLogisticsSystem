package academy.kalashnikov.control.presentation.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JwtResponse(
    @SerialName("jwt")
    val jwt: String
)
