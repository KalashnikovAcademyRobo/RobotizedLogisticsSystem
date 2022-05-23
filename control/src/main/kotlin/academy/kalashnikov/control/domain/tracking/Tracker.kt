package academy.kalashnikov.control.domain.tracking

import kotlinx.coroutines.flow.Flow

interface Tracker {
    val tracks: Flow<RobotTrack>

    suspend fun track(track: RobotTrack)
}
