package academy.kalashnikov.control.data.core.database

import academy.kalashnikov.control.data.item.ItemTable
import academy.kalashnikov.control.data.order.OrderTable
import academy.kalashnikov.control.data.robot.RobotTable
import academy.kalashnikov.control.data.status.OrderStatusEntity
import academy.kalashnikov.control.data.status.OrderStatusTable
import academy.kalashnikov.control.domain.status.OrderStatus
import academy.kalashnikov.control.presentation.core.delegates.ApplicationDelegate
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
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
        transaction {
            createTables()
            upsertStatuses()
        }
    }

    private fun createTables() {
        SchemaUtils.create(ItemTable, OrderStatusTable, OrderTable, RobotTable)
    }

    private fun upsertStatuses() {
        OrderStatusTable.deleteAll()
        for (status in OrderStatus.all) {
            OrderStatusEntity.new(status.id) {
                name = status.name
            }
        }
    }
}
