package academy.kalashnikov.control.data.auth

import academy.kalashnikov.control.domain.auth.JwtDataSource
import academy.kalashnikov.control.domain.auth.JwtGenerator
import academy.kalashnikov.control.domain.auth.JwtPayload
import academy.kalashnikov.control.domain.auth.JwtType
import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.presentation.auth.JwtValidator
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.impl.JWTParser
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.utils.io.printStack
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Date
import javax.inject.Inject

private const val ID = "id"
private const val TYPE = "type"

class JwtManager @Inject constructor(
    private val jwtDataSource: JwtDataSource,
    private val jwtVerifier: JWTVerifier,
    private val dispatchersProvider: DispatchersProvider
) : JwtValidator, JwtGenerator {
    override fun validate(credential: JWTCredential): JWTPrincipal? {
        val id = credential[ID]
        val type = try {
            JwtType.valueOf(credential[TYPE].orEmpty())
        } catch (e: IllegalArgumentException) {
            null
        }
        return if (id.isNullOrEmpty() || type == null) {
            null
        } else {
            JWTPrincipal(credential.payload)
        }
    }

    override fun retrievePayload(jwtPrincipal: JWTPrincipal): JwtPayload {
        val claim = requireNotNull(jwtPrincipal.payload.getClaim(ID).asString()) {
            "Principal payload does not contain a valid id"
        }
        val type = try {
            val stringType = jwtPrincipal.payload.getClaim(TYPE).asString().orEmpty()
            JwtType.valueOf(stringType)
        } catch (e: IllegalArgumentException) {
            null
        } ?: throw IllegalArgumentException("Principal payload does not contain a valid type")
        return JwtPayload(claim, type)
    }

    override fun retrievePayload(jwt: String): JwtPayload? {
        val credential = try {
            val encodedPayload = jwtVerifier.verify(jwt).payload
            val payloadContents = String(Base64.getUrlDecoder().decode(encodedPayload))
            JWTCredential(JWTParser().parsePayload(payloadContents))
        } catch (e: JWTVerificationException) {
            e.printStack()
            null
        }
        return credential?.let(::validate)
            ?.let(::retrievePayload)
    }

    override suspend fun generate(id: String, type: JwtType): String = withContext(dispatchersProvider.default) {
        val expirationDate = Date(Long.MAX_VALUE)
        val (secret, issuer, audience) = jwtDataSource.jwtData
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(ID, id)
            .withClaim(TYPE, type.toString())
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret))
    }
}
