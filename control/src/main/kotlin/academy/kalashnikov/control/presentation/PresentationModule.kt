package academy.kalashnikov.control.presentation

import academy.kalashnikov.control.presentation.auth.AuthModule
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegatesModule
import academy.kalashnikov.control.presentation.item.ItemModule
import academy.kalashnikov.control.presentation.order.OrderModule
import academy.kalashnikov.control.presentation.robot.RobotModule
import dagger.Module

@Module(
    includes = [
        ApplicationDelegatesModule::class,
        AuthModule::class,
        OrderModule::class,
        ItemModule::class,
        RobotModule::class
    ]
)
interface PresentationModule
