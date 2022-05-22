package academy.kalashnikov.control.presentation.core

import academy.kalashnikov.control.data.DataModule
import academy.kalashnikov.control.domain.DomainModule
import academy.kalashnikov.control.presentation.PresentationModule
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegatesModule
import dagger.BindsInstance
import dagger.Component
import io.ktor.server.application.Application
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface ApplicationComponent {
    @get:Initializer
    val initializers: Set<@JvmSuppressWildcards ApplicationDelegate>

    @get:Router
    val routers: Set<@JvmSuppressWildcards ApplicationDelegate>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
