package academy.kalashnikov.control.data.core.database

import academy.kalashnikov.control.presentation.core.Initializer
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface DatabaseModule {
    @Binds
    @Singleton
    @Initializer
    @IntoSet
    fun bindDatabaseInitializer(databaseInitializer: DatabaseInitializer): ApplicationDelegate
}
