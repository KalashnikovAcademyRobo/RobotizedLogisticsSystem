package academy.kalashnikov.control.data

import academy.kalashnikov.control.data.auth.AuthDataModule
import academy.kalashnikov.control.data.core.database.DatabaseModule
import dagger.Module

@Module(
    includes = [
        AuthDataModule::class,
        DatabaseModule::class
    ]
)
interface DataModule
