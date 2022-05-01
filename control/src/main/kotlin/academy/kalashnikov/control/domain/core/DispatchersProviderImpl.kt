package academy.kalashnikov.control.domain.core

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
    override val io = Dispatchers.IO
    override val unconfined = Dispatchers.Unconfined
}
