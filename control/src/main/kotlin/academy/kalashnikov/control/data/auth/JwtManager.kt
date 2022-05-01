package academy.kalashnikov.control.data.auth

import academy.kalashnikov.control.domain.auth.JwtDataSource
import academy.kalashnikov.control.domain.auth.JwtGenerator
import academy.kalashnikov.control.domain.auth.JwtPayload
import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.presentation.auth.JwtValidator
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.jwt.JWTCredential
import io.ktor.auth.jwt.JWTPrincipal
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val ID = "id"
private const val JWT_EXPIRY_PERIOD_IN_DAYS = 14L

class JwtManager @Inject constructor(
    private val jwtDataSource: JwtDataSource,
    private val dispatchersProvider: DispatchersProvider
) : JwtValidator, JwtGenerator {
    private val expiryPeriodMillis = TimeUnit.DAYS.toMillis(JWT_EXPIRY_PERIOD_IN_DAYS)

    override fun validate(credential: JWTCredential): JWTPrincipal? {
        val id = credential[ID]
        return if (id.isNullOrEmpty()) {
            null
        } else {
            JWTPrincipal(credential.payload)
        }
    }

    override fun retrievePayload(jwtPrincipal: JWTPrincipal): JwtPayload {
        val claim = requireNotNull(jwtPrincipal.payload.getClaim(ID).asString()) {
            "Principal payload does not contain a valid id"
        }
        return JwtPayload(claim)
    }

    override fun generate(id: String): String = with(dispatchersProvider.default) {
        val expirationDate = Date(System.currentTimeMillis() + expiryPeriodMillis)
        val (secret, issuer, audience) = jwtDataSource.jwtData
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(ID, id)
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret))
    }
}
