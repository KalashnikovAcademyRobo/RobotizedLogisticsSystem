package academy.kalashnikov.control.data.status

import academy.kalashnikov.control.domain.status.OrderStatus

fun OrderStatusEntity.toOrderStatus() = OrderStatus(
    id = id.value,
    name = name
)
