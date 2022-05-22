package academy.kalashnikov.control.domain.core.communication

import kotlinx.coroutines.Job

interface Communication {
    val job: Job

    suspend fun send(message: String): Boolean
    suspend fun receive(): String?
}
