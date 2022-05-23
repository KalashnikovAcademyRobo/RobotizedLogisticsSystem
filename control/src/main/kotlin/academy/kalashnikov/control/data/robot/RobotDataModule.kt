package academy.kalashnikov.control.data.robot

import academy.kalashnikov.control.domain.robot.RobotSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RobotDataModule {
    @Binds
    @Singleton
    fun bindRobotSource(robotDatabaseSource: RobotDatabaseSource): RobotSource
}
