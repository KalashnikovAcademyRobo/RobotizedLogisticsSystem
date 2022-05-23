package academy.kalashnikov.control.domain.core.navigation

import java.lang.Math.floorMod

enum class Direction(private val description: String) {
    FORWARD("forward"),
    RIGHT("right"),
    BACK("back"),
    LEFT("left");

    override fun toString() = description
}

fun Direction.relativeTo(other: Direction): Direction {
    val relativeIndex = floorMod(other.ordinal - ordinal, Direction.values().size)
    return Direction.values()[relativeIndex]
}
