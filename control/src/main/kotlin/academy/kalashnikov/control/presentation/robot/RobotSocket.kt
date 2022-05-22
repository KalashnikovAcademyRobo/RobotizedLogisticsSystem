package academy.kalashnikov.control.presentation.robot

import academy.kalashnikov.control.domain.core.DispatchersProvider
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.server.application.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PORT_PROPERTY = "tcp.port"

class RobotSocket @Inject constructor(
    private val application: Application,
    private val dispatchersProvider: DispatchersProvider,
    private val navigatorFactory: RobotNavigator.Factory
) : ApplicationDelegate {
    private val scope = CoroutineScope(dispatchersProvider.io)

    override fun onApplicationReady() {
        val port = application.environment
            .config
            .property(PORT_PROPERTY)
            .getString()
            .toInt()
        scope.launch {
            val socket = aSocket(ActorSelectorManager(dispatchersProvider.io)).tcp()
                .bind(port = port)
            while (true) {
                val navigator = navigatorFactory.create(socket.accept())
                scope.launch {
                    navigator.navigateRobot()
                }
            }
        }
    }
}
