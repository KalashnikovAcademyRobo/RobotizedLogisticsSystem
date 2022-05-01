package academy.kalashnikov.control.data.core.database

import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.application.Application
import org.jetbrains.exposed.sql.Database
import javax.inject.Inject

private const val URL = "database.url"
private const val USER = "database.user"
private const val PASSWORD = "database.password"

class DatabaseInitializer @Inject constructor(
    private val application: Application
) : ApplicationDelegate {
    override fun onApplicationReady() {
        with(application.environment.config) {
            Database.connect(
                url = property(URL).getString(),
                user = propertyOrNull(USER)?.getString().orEmpty(),
                password = propertyOrNull(PASSWORD)?.getString().orEmpty()
            )
        }
    }
}
