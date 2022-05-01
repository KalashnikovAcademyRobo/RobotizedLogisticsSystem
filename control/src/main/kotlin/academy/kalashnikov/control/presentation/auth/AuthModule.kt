package academy.kalashnikov.control.presentation.auth

import academy.kalashnikov.control.presentation.core.Initializer
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface AuthModule {
    @Binds
    @Singleton
    @Initializer
    @IntoSet
    fun bindJwtApplicationDelegate(
        jwtApplicationDelegate: JwtApplicationDelegate
    ): ApplicationDelegate
}
