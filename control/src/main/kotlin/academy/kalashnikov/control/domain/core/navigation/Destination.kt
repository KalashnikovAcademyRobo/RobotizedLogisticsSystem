package academy.kalashnikov.control.domain.core.navigation

import academy.kalashnikov.control.domain.core.geometry.Point

enum class Destination(val location: Point) {
    MANIPULATOR(Point.ORIGIN),
    USER(Point(4.0, 4.0))
}
