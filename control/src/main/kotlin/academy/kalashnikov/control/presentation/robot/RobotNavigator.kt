package academy.kalashnikov.control.presentation.robot

import academy.kalashnikov.control.domain.robot.state.RobotAuthorizationState
import academy.kalashnikov.control.domain.robot.state.RobotState
import academy.kalashnikov.control.presentation.core.communication.SocketCommunication
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.ktor.network.sockets.Socket

class RobotNavigator @AssistedInject constructor(
    private val authorizationStateFactory: RobotAuthorizationState.Factory,
    @Assisted private val socket: Socket
) {
    suspend fun navigateRobot() {
        var state: RobotState? = authorizationStateFactory.create(SocketCommunication(socket))
        while (state != null) {
            state = state.operate()
        }
        socket.close()
    }

    @AssistedFactory
    interface Factory {
        fun create(socket: Socket): RobotNavigator
    }
}
