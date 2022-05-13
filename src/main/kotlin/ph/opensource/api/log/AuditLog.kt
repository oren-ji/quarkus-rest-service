package ph.opensource.api.log

import io.opentracing.Tracer
import io.vertx.ext.web.RoutingContext
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import ph.opensource.api.constant.APIErrorMessage
import ph.opensource.api.log.enum.LogLevel
import ph.opensource.api.util.MachineUtil
import javax.enterprise.context.ApplicationScoped

/**
 * Audit log
 *
 * Purpose: Defines custom logger to audit system events.
 *
 * @property log
 * @property tracer
 * @constructor Create empty Audit log
 */
@ApplicationScoped
class AuditLog(
    private val log: Logger,
    private val tracer: Tracer,
) {
    /**
     * Service Name
     *
     * Purpose: Retrieves defined service-name on resources/application.properties
     */
    @ConfigProperty(name = "api.service-name")
    private lateinit var SERVICE_NAME: String

    private val SERVICE_LOCATION = "ipAddress=${MachineUtil.getMachineIpAddress()}"

    fun apiRequest(param: Any? = null, rc: RoutingContext) {
        info(
            message = "API request received.",
            data = mapOf(
                "remoteHostName" to rc.request().remoteAddress().hostName(),
                "remoteHostAddress" to rc.request().remoteAddress().hostAddress(),
                "remoteHostPort" to rc.request().remoteAddress().port(),
                "requestUrl" to rc.request().absoluteURI(),
                "requestMethod" to rc.request().method(),
                "requestBody" to param,
            )
        )
    }

    fun exception(traceId: String? = null, data: Map<String, Any?>? = null) {
        audit(LogLevel.ERROR, traceId, APIErrorMessage.FAILURE_NOTICE, data)
    }

    fun info(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.INFO, traceId, message, data)
    }

    fun warn(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.WARN, traceId, message, data)
    }

    fun debug(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.DEBUG, traceId, message, data)
    }

    fun trace(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.TRACE, traceId, message, data)
    }

    fun error(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.ERROR, traceId, message, data)
    }

    fun fatal(traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
        audit(LogLevel.FATAL, traceId, message, data)
    }

    private fun audit(level: LogLevel, traceId: String? = null, message: String, data: Map<String, Any?>? = null) {
         val correlationId = traceId ?: tracer.activeSpan().context().toTraceId()

        val dataContent = if (data != null) {
            "data: $data"
        } else {
            ""
        }

        val content = """
            ----
            trace-id: $correlationId
            service: $SERVICE_NAME
            location: $SERVICE_LOCATION
            message: $message
            $dataContent
        """.trimIndent()

        when(level) {
            LogLevel.INFO -> log.info(content)
            LogLevel.WARN -> log.warn(content)
            LogLevel.DEBUG -> log.debug(content)
            LogLevel.TRACE -> log.trace(content)
            LogLevel.ERROR -> log.error(content)
            LogLevel.FATAL -> log.fatal(content)
        }
    }
}