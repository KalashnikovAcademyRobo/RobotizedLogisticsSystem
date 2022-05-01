package academy.kalashnikov.control.presentation.item

import academy.kalashnikov.control.domain.core.DefaultContext
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import javax.inject.Inject

// TODO use orders sources for realistic mocking
class ItemRouter @Inject constructor(
    private val application: Application
) : ApplicationDelegate {
    override fun onApplicationReady() {
        application.routing {
            get("client/items") { getClientItems(this) }
        }
    }

    private suspend fun getClientItems(context: DefaultContext) {
        context.call.respond(emptyList<Unit>())
    }
}
