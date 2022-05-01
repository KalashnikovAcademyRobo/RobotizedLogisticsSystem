package academy.kalashnikov.control.domain.auth

interface JwtGenerator {
    fun generate(id: String): String
}
