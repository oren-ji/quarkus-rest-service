package ph.opensource.api.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.ext.web.handler.sockjs.impl.StringEscapeUtils
import org.apache.commons.compress.utils.IOUtils
import ph.opensource.api.constant.APIHeader
import ph.opensource.api.log.AuditLog
import java.io.ByteArrayInputStream
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.HttpMethod
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter

/**
 * Http client filter
 *
 * Purpose: This applies a filter for both client request and response
 *
 * @property log
 * @property mapper
 * @constructor Create empty Http client filter
 */
@ApplicationScoped
class HttpClientFilter(
    private val log: AuditLog,
    private val mapper: ObjectMapper
): ClientRequestFilter, ClientResponseFilter {

    /**
     * Filter
     *
     * Purpose: Applies filter on client request to perform audit log.
     *
     * @param requestContext
     */
    override fun filter(requestContext: ClientRequestContext?) {
        val bodiedHttpMethods = listOf(HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT)

        val traceId = requestContext?.getHeaderString(APIHeader.TRACE_ID) ?: "no-trace-id"

        val method = requestContext?.method
        val uri = requestContext?.uri
        var body = ""

        if (method?.indexOfAny(bodiedHttpMethods) == 0  && requestContext.hasEntity()) {
            val entityString = mapper.writeValueAsString(requestContext.entity)
            body = StringEscapeUtils.unescapeJava(entityString)
            body = maskSensitiveInformation(body)
        }

        log.info(
            traceId = traceId,
            message = "Attempting to request to client.",
            data = mapOf(
                "requestUri" to uri,
                "requestMethod" to method,
                "requestBody" to body,
            )
        )
    }

    /**
     * Filter
     *
     * Purpose: Applies filter on client response to perform audit log.
     *
     * @param requestContext
     * @param responseContext
     */
    override fun filter(requestContext: ClientRequestContext?, responseContext: ClientResponseContext?) {
        val traceId = requestContext?.getHeaderString(APIHeader.TRACE_ID) ?: "no-trace-id"

        val status = responseContext?.status
        val statusInfo = responseContext?.statusInfo?.reasonPhrase
        var body = ""

        if (responseContext?.hasEntity() == true) {
            if (status == 302) {
                body = "redirection skipped"
            } else {
                val entityStream = responseContext.entityStream
                val bytes = IOUtils.toByteArray(entityStream)
                responseContext.entityStream = ByteArrayInputStream(bytes)
                body = String(bytes)
            }
        }

        log.info(
            traceId = traceId,
            message = "Received response from client.",
            data = mapOf(
                "responseStatus" to "$status $statusInfo",
                "responseBody" to body,
            )
        )
    }

    /**
     * Mask sensitive information
     *
     * Purpose: This method masks sensitive information on the request body.
     *
     * Implementation: This can vary depending on the API being developed.
     *
     * @param body
     * @return
     */
    fun maskSensitiveInformation(body: String): String {
        var temp = body
        val data = mapper.readTree(body)
        var maskLength: Int

        /**
         * Client Id Partially Masked
         */
        val clientId = data.get("client_id").first().asText()
        maskLength = clientId.length / 2
        val maskedClientId = clientId.replaceRange(0, maskLength, "".padStart(maskLength, '*'))
        temp = temp.replace(clientId, maskedClientId)

        /**
         * Client Secret Fully Masked
         */
        val clientSecret = data.get("client_secret").first().asText()
        maskLength = clientSecret.length
        temp = temp.replace(clientSecret, "".padStart(maskLength, '*'))


        /**
         * Token Partially Masked
         */
        val token = data.get("token")?.first()?.asText()
        if (!token.isNullOrBlank()) {
            maskLength = token.length / 2
            val maskedToken = token.replaceRange(0, maskLength, "".padStart(maskLength, '*'))
            temp = temp.replace(token, maskedToken)
        }

        return temp
    }
}