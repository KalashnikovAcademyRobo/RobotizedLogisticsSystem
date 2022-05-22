package academy.kalashnikov.control.domain.robot.state.omniwheel

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.core.navigation.Destination
import academy.kalashnikov.control.domain.robot.state.RobotState
import academy.kalashnikov.control.domain.status.OmniwheelRobotStatusTracker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class OmniwheelPickingState @AssistedInject constructor(
    private val robotStatusTracker: OmniwheelRobotStatusTracker,
    private val drivingStateFactory: OmniwheelDrivingState.Factory,
    private val communication: Communication,
    @Assisted private val destination: Destination
) : RobotState {
    override suspend fun operate(): RobotState? = coroutineScope {
        val waitingJob = launch {
            when (destination) {
                Destination.MANIPULATOR -> waitForManipulator()
                Destination.USER -> waitForUser()
            }
        }
        select<Unit> {
            waitingJob.onJoin {}
            communication.job.onJoin {}
        }
        if (waitingJob.isActive) {
            waitingJob.cancelAndJoin()
            null
        } else when (destination) {
            Destination.MANIPULATOR -> drivingStateFactory.create(Destination.USER)
            Destination.USER -> drivingStateFactory.create(Destination.MANIPULATOR)
        }
    }

    private suspend fun waitForManipulator() = with(robotStatusTracker) {
        waitForManipulator()
        carryingOrderId.first { it != null }
    }

    private suspend fun waitForUser() = with(robotStatusTracker) {
        setCarryingOrderId(null)
        goingToManipulator.first { it }
    }

    @AssistedFactory
    interface Factory {
        fun create(destination: Destination): OmniwheelPickingState
    }
}
