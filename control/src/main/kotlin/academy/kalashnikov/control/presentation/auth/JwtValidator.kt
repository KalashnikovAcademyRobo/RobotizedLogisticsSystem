package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.domain.auth.JwtPayload
import io.ktor.auth.jwt.JWTCredential
import io.ktor.auth.jwt.JWTPrincipal

interface JwtValidator {
    fun validate(credential: JWTCredential): JWTPrincipal?
    fun retrievePayload(jwtPrincipal: JWTPrincipal): JwtPayload
}
