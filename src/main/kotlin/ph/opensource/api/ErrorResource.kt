package ph.opensource.api

import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.timeout.TimeoutException
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.core.http.HttpServerResponse
import org.jboss.resteasy.reactive.ClientWebApplicationException
import ph.opensource.api.constant.APIErrorMessage
import ph.opensource.api.log.AuditLog
import ph.opensource.api.model.ErrorResponse
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Error resource
 *
 * Purpose: Defines routing to handle failure.
 *
 * @property log
 * @constructor Create empty Error resource
 */
@ApplicationScoped
class ErrorResource(
    private var log: AuditLog,
) {
    @Route(
        type = Route.HandlerType.FAILURE,
        produces = [MediaType.APPLICATION_JSON]
    )
    fun onException(
        e: Exception,
        response: HttpServerResponse
    ): Uni<ErrorResponse> {
        this.log.info(message = "Router error handling triggered by exception $e.")

        return when (e) {
            is BadRequestException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.BAD_REQUEST.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = e.message ?: APIErrorMessage.INVALID_REQUEST,
                        )
                    }
            }
            is IllegalArgumentException,
            is ClientErrorException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.SERVICE_UNAVAILABLE.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = APIErrorMessage.TEMPORARILY_UNAVAILABLE,
                        )
                    }
            }
            is NotAcceptableException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.NOT_ACCEPTABLE.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "not acceptable",
                        )
                    }
            }
            is NotAllowedException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.METHOD_NOT_ALLOWED.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "method not allowed",
                        )
                    }
            }
            is io.quarkus.security.ForbiddenException,
            is ForbiddenException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.FORBIDDEN.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "forbidden",
                        )
                    }
            }
            is NotAuthorizedException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.UNAUTHORIZED.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "unauthorized",
                        )
                    }
            }
            is NotFoundException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.NOT_FOUND.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = e.message ?: "not found",
                        )
                    }
            }
            is NotSupportedException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "media type not supported",
                        )
                    }
            }
            is ProcessingException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.UNPROCESSABLE_ENTITY.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = e.message ?: "unprocessable",
                        )
                    }
            }
            is RedirectionException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.MISDIRECTED_REQUEST.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "misdirected request",
                        )
                    }
            }
            is ServiceUnavailableException  -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.SERVICE_UNAVAILABLE.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = e.message ?: APIErrorMessage.TEMPORARILY_UNAVAILABLE,
                        )
                    }
            }
            is ServerErrorException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "internal server error",
                        )
                    }
            }
            is WebApplicationException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = e.message ?: "unexpected web application error",
                        )
                    }
            }
            is TimeoutException -> {
                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.REQUEST_TIMEOUT .code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "request timeout",
                        )
                    }
            }
            is ClientWebApplicationException -> {
                val status = HttpResponseStatus.valueOf(e.response.status)

                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = status.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = status.reasonPhrase() ?: "unexpected client web application error",
                        )
                    }
            }
            else -> {
                this.log.info(message = "Gracefully handling unidentified/generic exception $e.")
                e.printStackTrace()

                Uni.createFrom().voidItem().onItem()
                    .invoke { _ ->
                        response.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()
                    }
                    .replaceWith {
                        ErrorResponse(
                            details = "internal server error",
                        )
                    }
            }
        }
    }
}