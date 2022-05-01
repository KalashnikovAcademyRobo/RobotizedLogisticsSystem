package academy.kalashnikov.control.presentation.core.delegates

import academy.kalashnikov.control.presentation.core.Initializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface ApplicationDelegatesModule {
    @Binds
    @Singleton
    @Initializer
    @IntoSet
    fun bindInstallFeaturesApplicationDelegate(
        installFeaturesApplicationDelegate: InstallFeaturesApplicationDelegate
    ): ApplicationDelegate
}
