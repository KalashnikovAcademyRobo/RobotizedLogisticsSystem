package academy.kalashnikov.control.data.item

import academy.kalashnikov.control.domain.item.Item

fun ItemEntity.toItem() = Item(
    id = id.value,
    name = name,
    quantity = quantity
)
