package academy.kalashnikov.control.data.robot

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RobotEntity(id: EntityID<String>) : Entity<String>(id) {
    var type by RobotTable.type

    companion object : EntityClass<String, RobotEntity>(RobotTable)
}
