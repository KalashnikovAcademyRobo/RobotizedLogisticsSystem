package academy.kalashnikov.control.data

import academy.kalashnikov.control.data.auth.AuthDataModule
import academy.kalashnikov.control.data.core.database.DatabaseModule
import academy.kalashnikov.control.data.item.ItemDataModule
import academy.kalashnikov.control.data.order.OrderDataModule
import dagger.Module

@Module(
    includes = [
        AuthDataModule::class,
        DatabaseModule::class,
        OrderDataModule::class,
        ItemDataModule::class
    ]
)
interface DataModule
