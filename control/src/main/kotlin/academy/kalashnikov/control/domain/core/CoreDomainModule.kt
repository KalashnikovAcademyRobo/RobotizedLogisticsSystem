package academy.kalashnikov.control.domain.core

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(subcomponents = [RobotComponent::class])
interface CoreDomainModule {
    @Binds
    @Singleton
    fun bindDispatchersProvider(
        dispatchersProviderImpl: DispatchersProviderImpl
    ): DispatchersProvider
}
