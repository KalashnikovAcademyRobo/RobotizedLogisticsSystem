package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.domain.auth.JwtDataSource
import academy.kalashnikov.control.presentation.core.Initializer
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module(includes = [AuthModule.JwtVerifierModule::class])
interface AuthModule {
    @Binds
    @Singleton
    @Initializer
    @IntoSet
    fun bindJwtApplicationDelegate(
        jwtApplicationDelegate: JwtApplicationDelegate
    ): ApplicationDelegate

    @Module
    class JwtVerifierModule {
        @Provides
        @Singleton
        fun provideJwtVerifier(jwtDataSource: JwtDataSource): JWTVerifier {
            val (secret, issuer, audience) = jwtDataSource.jwtData
            return JWT.require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
        }
    }
}
