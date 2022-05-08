package academy.kalashnikov.control.data.status

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class OrderStatusEntity(id: EntityID<String>) : Entity<String>(id) {
    var name by OrderStatusTable.name

    companion object : EntityClass<String, OrderStatusEntity>(OrderStatusTable)
}
