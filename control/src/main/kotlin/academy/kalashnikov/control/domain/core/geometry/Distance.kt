package academy.kalashnikov.control.domain.core.geometry

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.hypot
import kotlin.math.sign

fun Point.distanceTo(other: Point) = hypot(x - other.x, y - other.y)

fun Point.angleTo(other: Point): Double = if (other == Point.ORIGIN) {
    val angle = asin(x / distanceTo(Point.ORIGIN)) + PI / 2
    angle * -sign(y)
} else {
    Point(x = x - other.x, y = y - other.y).angleTo(Point.ORIGIN)
}
