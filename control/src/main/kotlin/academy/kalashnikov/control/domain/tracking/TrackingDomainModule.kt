package academy.kalashnikov.control.domain.tracking

import dagger.Binds
import javax.inject.Singleton

@dagger.Module
interface TrackingDomainModule {
    @Binds
    @Singleton
    fun bindTracker(trackerImpl: TrackerImpl): Tracker
}
