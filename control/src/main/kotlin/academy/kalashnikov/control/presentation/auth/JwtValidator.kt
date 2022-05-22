package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.domain.auth.JwtPayload
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

interface JwtValidator {
    fun validate(credential: JWTCredential): JWTPrincipal?
    fun retrievePayload(jwtPrincipal: JWTPrincipal): JwtPayload
    fun retrievePayload(jwt: String): JwtPayload?
}
