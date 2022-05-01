package academy.kalashnikov.control.domain.auth

data class JwtData(
    val secret: String,
    val issuer: String,
    val audience: String
)
