package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.domain.auth.JwtDataSource
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import javax.inject.Inject

const val AUTH_NAME = "auth_jwt"

class JwtApplicationDelegate @Inject constructor(
    private val application: Application,
    private val jwtDataSource: JwtDataSource,
    private val jwtValidator: JwtValidator
) : ApplicationDelegate {
    override fun onApplicationReady() {
        val (secret, issuer, audience) = jwtDataSource.jwtData
        application.install(Authentication) {
            jwt(AUTH_NAME) {
                val verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
                verifier(verifier)
                validate { jwtValidator.validate(it) }
            }
        }
    }
}
