package academy.kalashnikov.control.presentation.core.communication

import academy.kalashnikov.control.domain.core.communication.Communication
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeStringUtf8
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SocketCommunication(socket: Socket) : Communication {
    override val job: Job

    private val writeChannel = socket.openWriteChannel(autoFlush = true)
    private val readChannel: ByteReadChannel

    init {
        val channel = ByteChannel(autoFlush = false)
        readChannel = channel
        job = socket.attachForReading(channel)
    }

    override suspend fun send(message: String): Boolean = try {
        writeChannel.writeStringUtf8(message + '\n')
        true
    } catch (throwable: Throwable) {
        false
    }

    override suspend fun receive(): String? = readChannel.readUTF8Line()
}
