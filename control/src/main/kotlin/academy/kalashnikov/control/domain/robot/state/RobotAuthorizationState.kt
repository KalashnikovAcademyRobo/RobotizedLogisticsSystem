package academy.kalashnikov.control.domain.robot.state

import academy.kalashnikov.control.domain.auth.JwtType
import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.robot.RobotSource
import academy.kalashnikov.control.presentation.auth.JwtValidator
import academy.kalashnikov.control.domain.core.RobotComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RobotAuthorizationState @AssistedInject constructor(
    private val jwtValidator: JwtValidator,
    private val robotSource: RobotSource,
    private val robotComponentFactory: RobotComponent.Factory,
    @Assisted private val communication: Communication
) : RobotState {
    override suspend fun operate(): RobotState? {
        var command = communication.receive()
        while (command != null) {
            val args = command.split(" ")
            if (args.size == 2 && args[0] == "authorize") {
                authorize(args[1])?.let { return it }
            }
            communication.send("bad command")
            command = communication.receive()
        }
        return null
    }

    private suspend fun authorize(jwt: String): RobotState? {
        val robot = jwtValidator.retrievePayload(jwt)
            ?.takeIf { it.type == JwtType.ROBOT }
            ?.let { robotSource.findRobotById(it.id) }
        return robot?.let {
            robotComponentFactory.create(robot.id, communication)
                .initialStates[robot.type]
                ?.get()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(communication: Communication): RobotAuthorizationState
    }
}
