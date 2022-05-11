package academy.kalashnikov.control.presentation.item

import academy.kalashnikov.control.domain.core.DefaultContext
import academy.kalashnikov.control.domain.item.Item
import academy.kalashnikov.control.domain.item.ItemSource
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import javax.inject.Inject

// TODO use orders sources for realistic mocking
class ItemRouter @Inject constructor(
    private val application: Application,
    private val itemSource: ItemSource
) : ApplicationDelegate {
    override fun onApplicationReady() {
        application.routing {
            get("client/items") { getClientItems(this) }
            post("warehouse/item", ::upsertItem)
        }
    }

    private suspend fun getClientItems(context: DefaultContext) {
        context.call.respond(itemSource.getItems())
    }

    private suspend fun upsertItem(context: DefaultContext, item: Item) {
        itemSource.upsertItem(item)
        context.call.respond(HttpStatusCode.OK)
    }
}
