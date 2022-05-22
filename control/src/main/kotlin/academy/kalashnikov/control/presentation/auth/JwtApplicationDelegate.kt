package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import javax.inject.Inject

const val AUTH_NAME = "auth_jwt"

class JwtApplicationDelegate @Inject constructor(
    private val application: Application,
    private val jwtVerifier: JWTVerifier,
    private val jwtValidator: JwtValidator
) : ApplicationDelegate {
    override fun onApplicationReady() {
        application.install(Authentication) {
            jwt(AUTH_NAME) {
                verifier(jwtVerifier)
                validate { jwtValidator.validate(it) }
            }
        }
    }
}
