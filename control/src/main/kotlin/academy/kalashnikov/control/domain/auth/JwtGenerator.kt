package academy.kalashnikov.control.domain.auth

interface JwtGenerator {
    suspend fun generate(id: String, type: JwtType): String
}
