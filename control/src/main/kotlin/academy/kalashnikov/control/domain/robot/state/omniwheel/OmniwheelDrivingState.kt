package academy.kalashnikov.control.domain.robot.state.omniwheel

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.core.geometry.angleTo
import academy.kalashnikov.control.domain.core.geometry.distanceTo
import academy.kalashnikov.control.domain.core.geometry.inDegrees
import academy.kalashnikov.control.domain.core.geometry.modAbsolutePi
import academy.kalashnikov.control.domain.core.navigation.Destination
import academy.kalashnikov.control.domain.robot.state.RobotState
import academy.kalashnikov.control.domain.tracking.Tracker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlin.math.roundToLong

private const val STOP_DISTANCE = 0.5

class OmniwheelDrivingState @AssistedInject constructor(
    private val tracker: Tracker,
    private val robotId: String,
    private val communication: Communication,
    private val omniwheelPickingStateFactory: OmniwheelPickingState.Factory,
    @Assisted private val destination: Destination
) : RobotState {
    override suspend fun operate(): RobotState? = coroutineScope {
        val trackJob = launch { trackAndNavigate() }
        select<Unit> {
            trackJob.onJoin {}
            communication.job.onJoin {}
        }
        if (trackJob.isActive) {
            trackJob.cancelAndJoin()
            null
        } else {
            communication.send("stop")
            omniwheelPickingStateFactory.create(destination)
        }
    }

    private suspend fun trackAndNavigate() {
        tracker.tracks
            .filter { it.robotId == robotId }
            .takeWhile { it.location.distanceTo(destination.location) >= STOP_DISTANCE }
            .collect {
                val angle = it.location
                    .angleTo(destination.location)
                    .minus(it.rotation)
                    .modAbsolutePi()
                    .inDegrees()
                    .roundToLong()
                communication.send("turn $angle")
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(destination: Destination): OmniwheelDrivingState
    }
}
