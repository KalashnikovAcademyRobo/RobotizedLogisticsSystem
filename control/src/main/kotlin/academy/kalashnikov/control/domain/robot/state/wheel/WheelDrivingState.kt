package academy.kalashnikov.control.domain.robot.state.wheel

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.core.navigation.Destination
import academy.kalashnikov.control.domain.core.navigation.relativeTo
import academy.kalashnikov.control.domain.robot.state.RobotState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Provider

class WheelDrivingState @AssistedInject constructor(
    private val route: RobotRoute,
    private val communication: Communication,
    private val wheelPickingStateFactory: WheelPickingState.Factory,
    private val wheelProcessingState: Provider<WheelProcessingState>,
    @Assisted private val destination: Destination
) : RobotState {
    override suspend fun operate(): RobotState? {
        move()
        while (wait() && route.node.isNoStopPoint) {
            move()
        }
        return if (communication.job.isActive) {
            when (destination) {
                Destination.USER -> wheelProcessingState.get()
                Destination.MANIPULATOR -> wheelPickingStateFactory.create(destination)
            }
        } else {
            null
        }
    }

    private suspend fun move() = with(route) {
        val (nextNode, nextDirection) = route.node.neighbors.first()
        val moveDirection = route.direction.relativeTo(nextDirection)
        communication.send("move $moveDirection")
        node = nextNode
        direction = nextDirection
    }

    private suspend fun wait(): Boolean {
        var command = communication.receive()
        while (command != null) {
            if (command == "crossroad") {
                return true
            }
            command = communication.receive()
        }
        return false
    }

    @AssistedFactory
    interface Factory {
        fun create(destination: Destination): WheelDrivingState
    }
}
