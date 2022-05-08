package academy.kalashnikov.control.domain.item

interface ItemSource {
    suspend fun getItems(): List<Item>
    suspend fun upsertItem(item: Item)
}
