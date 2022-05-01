package academy.kalashnikov.control

import academy.kalashnikov.control.presentation.core.DaggerApplicationComponent
import io.ktor.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.mainModule() {
    val component = DaggerApplicationComponent.factory()
        .create(this)
    for (initializer in component.initializers) {
        initializer.onApplicationReady()
    }
    for (router in component.routers) {
        router.onApplicationReady()
    }
}
