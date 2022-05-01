package academy.kalashnikov.control.presentation

import academy.kalashnikov.control.presentation.auth.AuthModule
import academy.kalashnikov.control.presentation.item.ItemModule
import academy.kalashnikov.control.presentation.order.OrderModule
import dagger.Module

@Module(
    includes = [
        AuthModule::class,
        OrderModule::class,
        ItemModule::class
    ]
)
interface PresentationModule
