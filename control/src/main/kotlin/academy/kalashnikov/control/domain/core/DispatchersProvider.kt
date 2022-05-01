package academy.kalashnikov.control.domain.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
