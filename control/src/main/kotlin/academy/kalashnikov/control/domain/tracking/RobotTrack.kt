package academy.kalashnikov.control.domain.tracking

import academy.kalashnikov.control.domain.core.geometry.Point

data class RobotTrack(
    val rotation: Double,
    val location: Point,
    val robotId: String
)
