package academy.kalashnikov.control.presentation.item

import academy.kalashnikov.control.presentation.core.Router
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface ItemModule {
    @Binds
    @Singleton
    @Router
    @IntoSet
    fun bindItemRouter(itemRouter: ItemRouter): ApplicationDelegate
}
