package academy.kalashnikov.control.data.auth

import academy.kalashnikov.control.domain.auth.JwtDataSource
import academy.kalashnikov.control.domain.auth.JwtGenerator
import academy.kalashnikov.control.presentation.auth.JwtValidator
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AuthDataModule {
    @Binds
    @Singleton
    fun bindJwtGenerator(jwtManager: JwtManager): JwtGenerator

    @Binds
    @Singleton
    fun bindJwtValidator(jwtManager: JwtManager): JwtValidator

    @Binds
    @Singleton
    fun bindJwtDataSource(jwtDataSourceImpl: JwtDataSourceImpl): JwtDataSource
}
