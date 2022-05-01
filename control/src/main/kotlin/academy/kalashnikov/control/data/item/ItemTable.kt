package academy.kalashnikov.control.data.item

import academy.kalashnikov.control.data.core.database.DEFAULT_VARCHAR_LENGTH
import academy.kalashnikov.control.data.core.database.StringIdTable

object ItemTable : StringIdTable() {
    val name = varchar("id", DEFAULT_VARCHAR_LENGTH)
    val quantity = integer("quantity")
}
