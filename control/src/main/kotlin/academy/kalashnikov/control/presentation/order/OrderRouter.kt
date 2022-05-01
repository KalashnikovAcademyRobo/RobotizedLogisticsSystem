package academy.kalashnikov.control.presentation.order

import academy.kalashnikov.control.domain.core.DefaultContext
import academy.kalashnikov.control.domain.core.id.IdRequest
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import javax.inject.Inject

// TODO use orders sources for realistic mocking
class OrderRouter @Inject constructor(
    private val application: Application
) : ApplicationDelegate {
    override fun onApplicationReady() {
        application.routing {
            route("client") {
                get("orders") { getClientOrders(this) }
                post("order", ::clientOrder)
                post("order/confirm", ::confirmClientOrder)
            }
            route("warehouse") {
                get("orders") { getWarehouseOrders(this) }
                post("order/confirm", ::confirmWarehouseOrder)
            }
        }
    }

    private suspend fun getClientOrders(context: DefaultContext) {
        context.call.respond(emptyList<Unit>())
    }

    private suspend fun clientOrder(context: DefaultContext, request: IdRequest) {
        context.call.respond(HttpStatusCode.NotFound)
    }

    private suspend fun confirmClientOrder(context: DefaultContext, request: IdRequest) {
        context.call.respond(HttpStatusCode.NotFound)
    }

    private suspend fun getWarehouseOrders(context: DefaultContext) {
        context.call.respond(emptyList<Unit>())
    }

    private suspend fun confirmWarehouseOrder(context: DefaultContext, request: IdRequest) {
        context.call.respond(HttpStatusCode.NotFound)
    }
}
