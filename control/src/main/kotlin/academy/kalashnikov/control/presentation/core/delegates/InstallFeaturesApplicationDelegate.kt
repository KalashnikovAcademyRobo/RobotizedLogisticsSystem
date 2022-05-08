package academy.kalashnikov.control.presentation.core.delegates

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.SerializationException
import javax.inject.Inject

class InstallFeaturesApplicationDelegate @Inject constructor(
    private val application: Application
) : ApplicationDelegate {
    override fun onApplicationReady() {
        with(application) {
            install(ContentNegotiation) {
                json()
            }
            install(Compression) {
                gzip()
            }
            install(StatusPages) {
                exception<SerializationException> { call, _ ->
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            install(CallLogging)
        }
    }
}
