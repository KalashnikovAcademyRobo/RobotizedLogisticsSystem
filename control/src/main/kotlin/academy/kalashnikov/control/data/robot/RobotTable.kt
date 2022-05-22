package academy.kalashnikov.control.data.robot

import academy.kalashnikov.control.data.core.database.StringIdTable
import academy.kalashnikov.control.domain.robot.RobotType

object RobotTable : StringIdTable() {
    val type = enumeration<RobotType>("type")
}
