package academy.kalashnikov.control.domain.robot.state

import academy.kalashnikov.control.domain.robot.RobotType
import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class RobotTypeKey(val robotType: RobotType)
