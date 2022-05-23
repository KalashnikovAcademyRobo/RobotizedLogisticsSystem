package academy.kalashnikov.control.data.order

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WarehouseOrderEntity(id: EntityID<String>) : Entity<String>(id) {
    val itemId by OrderTable.itemId
    var statusId by OrderTable.statusId

    companion object : EntityClass<String, WarehouseOrderEntity>(OrderTable)
}
