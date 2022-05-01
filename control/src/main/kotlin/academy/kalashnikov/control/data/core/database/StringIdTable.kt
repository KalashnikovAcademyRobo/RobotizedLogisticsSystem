package academy.kalashnikov.control.data.core.database

import org.jetbrains.exposed.dao.id.IdTable

open class StringIdTable : IdTable<String>() {
    final override val id = varchar("id", DEFAULT_VARCHAR_LENGTH).entityId()
    final override val primaryKey = PrimaryKey(id)
}
