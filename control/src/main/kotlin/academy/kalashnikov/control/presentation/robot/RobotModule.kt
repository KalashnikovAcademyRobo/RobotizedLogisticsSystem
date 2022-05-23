package academy.kalashnikov.control.presentation.robot

import academy.kalashnikov.control.presentation.core.Router
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
interface RobotModule {
    @Binds
    @Singleton
    @Router
    @IntoSet
    fun bindRobotSocket(robotSocket: RobotSocket): ApplicationDelegate

    @Binds
    @Singleton
    @Router
    @IntoSet
    fun bindRobotRouter(robotRouter: RobotRouter): ApplicationDelegate
}
