package academy.kalashnikov.control.domain.core

import io.ktor.server.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext

typealias DefaultContext = PipelineContext<Unit, ApplicationCall>
