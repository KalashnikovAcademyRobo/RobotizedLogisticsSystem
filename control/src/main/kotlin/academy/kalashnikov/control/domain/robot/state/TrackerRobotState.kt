package academy.kalashnikov.control.domain.robot.state

import academy.kalashnikov.control.domain.core.communication.Communication
import academy.kalashnikov.control.domain.core.geometry.Point
import academy.kalashnikov.control.domain.core.geometry.inRadians
import academy.kalashnikov.control.domain.tracking.RobotTrack
import academy.kalashnikov.control.domain.tracking.Tracker
import javax.inject.Inject

class TrackerRobotState @Inject constructor(
    private val tracker: Tracker,
    private val communication: Communication
) : RobotState {
    override suspend fun operate(): RobotState? {
        var command = communication.receive()
        while (command != null) {
            val args = command.split("/")
            val track = RobotTrack(
                rotation = args[0].toDouble().inRadians(),
                location = Point(args[1].toDouble(), args[2].toDouble()),
                robotId = args[3]
            )
            tracker.track(track)
            command = communication.receive()
        }
        return null
    }
}
