package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.data.status.toOrderStatus
import academy.kalashnikov.control.domain.order.ClientOrder
import academy.kalashnikov.control.domain.order.WarehouseOrder

fun ClientOrderEntity.toOrder() = ClientOrder(
    id = id.value,
    itemId = itemId.value,
    status = status.toOrderStatus()
)

fun WarehouseOrderEntity.toOrder() = WarehouseOrder(
    id = id.value,
    itemId = itemId.value
)
