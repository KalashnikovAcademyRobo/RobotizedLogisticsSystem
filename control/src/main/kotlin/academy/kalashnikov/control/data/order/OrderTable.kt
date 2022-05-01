package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.data.core.database.StringIdTable
import academy.kalashnikov.control.data.item.ItemTable
import academy.kalashnikov.control.data.status.StatusTable

object OrderTable : StringIdTable() {
    val itemId = reference("item_id", ItemTable)
    val statusId = reference("status_id", StatusTable)
}
