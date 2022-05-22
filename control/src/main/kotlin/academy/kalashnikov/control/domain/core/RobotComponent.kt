package academy.kalashnikov.control.domain.core

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.robot.RobotType
import academy.kalashnikov.control.domain.robot.state.RobotState
import academy.kalashnikov.control.domain.robot.state.RobotStateModule
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Provider

@RobotScoped
@Subcomponent(modules = [RobotStateModule::class])
interface RobotComponent {
    val initialStates: Map<RobotType, @JvmSuppressWildcards Provider<RobotState>>

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance robotId: String,
            @BindsInstance communication: Communication
        ): RobotComponent
    }
}
