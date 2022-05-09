package academy.kalashnikov.control.data.status

import academy.kalashnikov.control.data.core.database.DEFAULT_VARCHAR_LENGTH
import academy.kalashnikov.control.data.core.database.StringIdTable

object OrderStatusTable : StringIdTable() {
    val name = varchar("name", DEFAULT_VARCHAR_LENGTH)
}
