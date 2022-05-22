package academy.kalashnikov.control.domain.core.geometry

data class Point(
    val x: Double,
    val y: Double
) {
    companion object {
        val ORIGIN = Point(0.0, 0.0)
    }
}
