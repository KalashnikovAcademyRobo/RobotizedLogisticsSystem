package academy.kalashnikov.control.data.status

import academy.kalashnikov.control.data.core.database.DEFAULT_VARCHAR_LENGTH
import academy.kalashnikov.control.data.core.database.StringIdTable
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Table

object StatusTable : StringIdTable() {
    val name = varchar("name", DEFAULT_VARCHAR_LENGTH)
}
