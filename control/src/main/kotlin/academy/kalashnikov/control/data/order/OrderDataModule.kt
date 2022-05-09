package academy.kalashnikov.control.data.order

import academy.kalashnikov.control.domain.order.OrderSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface OrderDataModule {
    @Binds
    @Singleton
    fun bindOrderSource(databaseOrderSource: DatabaseOrderSource): OrderSource
}
