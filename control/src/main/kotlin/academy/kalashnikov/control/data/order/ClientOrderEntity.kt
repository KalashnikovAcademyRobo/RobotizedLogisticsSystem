package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.data.status.OrderStatusEntity
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ClientOrderEntity(id: EntityID<String>) : Entity<String>(id) {
    var itemId by OrderTable.itemId
    var status by OrderStatusEntity referencedOn OrderTable.statusId

    companion object : EntityClass<String, ClientOrderEntity>(OrderTable)
}
