package academy.kalashnikov.control.domain.robot.state

import academy.kalashnikov.control.domain.core.RobotScoped
import academy.kalashnikov.control.domain.core.navigation.Destination
import academy.kalashnikov.control.domain.robot.RobotType
import academy.kalashnikov.control.domain.robot.state.omniwheel.OmniwheelDrivingState
import academy.kalashnikov.control.domain.robot.state.wheel.RobotRoute
import academy.kalashnikov.control.domain.robot.state.wheel.RobotRouteImpl
import academy.kalashnikov.control.domain.robot.state.wheel.WheelProcessingState
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [RobotStateModule.OmniwheelModule::class])
interface RobotStateModule {
    @Binds
    @RobotScoped
    @IntoMap
    @RobotTypeKey(RobotType.WHEEL)
    fun bindWheelRobotState(wheelProcessingState: WheelProcessingState): RobotState

    @Binds
    @RobotScoped
    @IntoMap
    @RobotTypeKey(RobotType.MANIPULATOR)
    fun bindManipulatorRobotState(manipulatorRobotState: ManipulatorRobotState): RobotState

    @Binds
    @RobotScoped
    @IntoMap
    @RobotTypeKey(RobotType.TRACKER)
    fun bindTrackerRobotState(trackerRobotState: TrackerRobotState): RobotState

    @Binds
    @RobotScoped
    fun bindRobotRoute(robotRouteImpl: RobotRouteImpl): RobotRoute

    @Module
    class OmniwheelModule {
        @Provides
        @RobotScoped
        @IntoMap
        @RobotTypeKey(RobotType.OMNIWHEEL)
        fun provideOmniwheelRobotState(
            omniwheelDrivingStateFactory: OmniwheelDrivingState.Factory
        ): RobotState = omniwheelDrivingStateFactory.create(Destination.MANIPULATOR)
    }
}
