package academy.kalashnikov.control.presentation.order

import academy.kalashnikov.control.presentation.core.Router
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface OrderModule {
    @Binds
    @Singleton
    @Router
    @IntoSet
    fun bindOrderRouter(orderRouter: OrderRouter): ApplicationDelegate
}
