package academy.kalashnikov.control.domain

import academy.kalashnikov.control.domain.core.CoreDomainModule
import dagger.Module

@Module(includes = [CoreDomainModule::class])
interface DomainModule
