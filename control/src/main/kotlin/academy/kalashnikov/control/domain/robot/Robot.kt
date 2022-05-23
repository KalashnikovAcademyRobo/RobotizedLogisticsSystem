package academy.kalashnikov.control.domain.robot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Robot(
    @SerialName("id")
    val id: String,

    @SerialName("type")
    val type: RobotType
)
