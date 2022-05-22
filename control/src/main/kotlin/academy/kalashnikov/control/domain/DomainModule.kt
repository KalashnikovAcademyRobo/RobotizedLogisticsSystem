package academy.kalashnikov.control.domain

import academy.kalashnikov.control.domain.core.CoreDomainModule
import academy.kalashnikov.control.domain.status.StatusDomainModule
import academy.kalashnikov.control.domain.tracking.TrackingDomainModule
import dagger.Module

@Module(
    includes = [
        CoreDomainModule::class,
        TrackingDomainModule::class,
        StatusDomainModule::class
    ]
)
interface DomainModule
