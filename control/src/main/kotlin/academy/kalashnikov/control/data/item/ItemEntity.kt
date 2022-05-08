package academy.kalashnikov.control.data.item

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ItemEntity(id: EntityID<String>) : Entity<String>(id) {
    var name by ItemTable.name
    var quantity by ItemTable.quantity

    companion object : EntityClass<String, ItemEntity>(ItemTable)
}
