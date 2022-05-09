package academy.kalashnikov.control.data.item

import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.domain.item.Item
import academy.kalashnikov.control.domain.item.ItemSource
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.inject.Inject

class DatabaseItemSource @Inject constructor(
    private val dispatchersProvider: DispatchersProvider
) : ItemSource {
    override suspend fun getItems(): List<Item> = newSuspendedTransaction(dispatchersProvider.io) {
        ItemEntity.all()
            .map { it.toItem() }
    }

    override suspend fun upsertItem(item: Item) {
        newSuspendedTransaction(dispatchersProvider.io) {
            val existing = ItemEntity.findById(item.id)
            existing?.delete()
            ItemEntity.new(item.id) {
                name = item.name
                quantity = item.quantity
            }
        }
    }
}
