package academy.kalashnikov.control.domain.auth

data class JwtPayload(
    val id: String,
    val type: JwtType
)
