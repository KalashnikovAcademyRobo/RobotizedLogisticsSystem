package academy.kalashnikov.control.presentation.order

import academy.kalashnikov.control.domain.core.DefaultContext
import academy.kalashnikov.control.domain.core.id.IdRequest
import academy.kalashnikov.control.domain.order.OrderSource
import academy.kalashnikov.control.domain.order.Outcome
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import javax.inject.Inject

// TODO use orders sources for realistic mocking
class OrderRouter @Inject constructor(
    private val application: Application,
    private val orderSource: OrderSource
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
        context.call.respond(orderSource.getClientOrders())
    }

    private suspend fun clientOrder(context: DefaultContext, request: IdRequest) {
        val statusCode = when (orderSource.createOrder(request.id)) {
            Outcome.SUCCESS -> HttpStatusCode.OK
            Outcome.NOT_FOUND -> HttpStatusCode.NotFound
            Outcome.CONFLICT -> HttpStatusCode.Conflict
        }
        context.call.respond(statusCode)
    }

    private suspend fun confirmClientOrder(context: DefaultContext, request: IdRequest) {
        val statusCode = when (orderSource.confirmClientOrder(request.id)) {
            Outcome.SUCCESS -> HttpStatusCode.OK
            Outcome.NOT_FOUND -> HttpStatusCode.NotFound
            Outcome.CONFLICT -> HttpStatusCode.Forbidden
        }
        context.call.respond(statusCode)
    }

    private suspend fun getWarehouseOrders(context: DefaultContext) {
        context.call.respond(orderSource.getWarehouseOrders())
    }

    private suspend fun confirmWarehouseOrder(context: DefaultContext, request: IdRequest) {
        val statusCode = when (orderSource.confirmWarehouseOrder(request.id)) {
            Outcome.SUCCESS -> HttpStatusCode.OK
            Outcome.NOT_FOUND -> HttpStatusCode.NotFound
            Outcome.CONFLICT -> HttpStatusCode.Forbidden
        }
        context.call.respond(statusCode)
    }
}
