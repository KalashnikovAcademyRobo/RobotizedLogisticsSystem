package academy.kalashnikov.control.data.item

import academy.kalashnikov.control.domain.item.ItemSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ItemDataModule {
    @Binds
    @Singleton
    fun bindItemSource(databaseItemSource: DatabaseItemSource): ItemSource
}
