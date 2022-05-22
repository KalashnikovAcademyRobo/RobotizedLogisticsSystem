package academy.kalashnikov.control.domain.core.geometry

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.floor

private const val DEGREES_IN_PI = 180

fun Double.inDegrees() = times(DEGREES_IN_PI) / PI

fun Double.inRadians() = times(PI) / DEGREES_IN_PI

fun Double.modAbsolutePi(): Double {
    val term = 2 * PI * floor((abs(this) + PI) / 2 / PI)
    return when {
        this > PI -> minus(term)
        this <= -PI -> plus(term)
        else -> this
    }
}
