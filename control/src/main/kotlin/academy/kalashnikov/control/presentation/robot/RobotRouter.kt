package academy.kalashnikov.control.presentation.robot

import academy.kalashnikov.control.domain.auth.JwtGenerator
import academy.kalashnikov.control.domain.auth.JwtType
import academy.kalashnikov.control.presentation.core.DefaultContext
import academy.kalashnikov.control.domain.robot.Robot
import academy.kalashnikov.control.domain.robot.RobotSource
import academy.kalashnikov.control.presentation.auth.JwtResponse
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import javax.inject.Inject

class RobotRouter @Inject constructor(
    private val robotSource: RobotSource,
    private val application: Application,
    private val jwtGenerator: JwtGenerator
) : ApplicationDelegate {
    override fun onApplicationReady() {
        application.routing {
            post("warehouse/robot", ::createRobot)
        }
    }

    private suspend fun createRobot(context: DefaultContext, robot: Robot) {
        robotSource.upsertRobot(robot)
        val token = jwtGenerator.generate(robot.id, JwtType.ROBOT)
        context.call.respond(JwtResponse(token))
    }
}
