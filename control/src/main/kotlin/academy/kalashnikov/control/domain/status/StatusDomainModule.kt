package academy.kalashnikov.control.domain.status

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface StatusDomainModule {
    @Binds
    @Singleton
    fun bindWheelRobotStatusTracker(robotStatusTrackerImpl: WheelRobotStatusTrackerImpl): WheelRobotStatusTracker

    @Binds
    @Singleton
    fun bindOmniwheelRobotStatusTracker(
        omniwheelRobotStatusTrackerImpl: OmniwheelRobotStatusTrackerImpl
    ): OmniwheelRobotStatusTracker
}
