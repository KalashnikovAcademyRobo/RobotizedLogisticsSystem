package academy.kalashnikov.control.domain.tracking

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class TrackerImpl @Inject constructor() : Tracker {
    override val tracks = MutableSharedFlow<RobotTrack>()

    override suspend fun track(track: RobotTrack) {
        tracks.emit(track)
    }
}
