package ph.opensource.api

import io.quarkus.vertx.web.Header
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import org.eclipse.microprofile.opentracing.Traced
import ph.opensource.api.constant.APIHeader
import ph.opensource.api.log.AuditLog
import ph.opensource.api.model.SuccessResponse
import ph.opensource.api.service.AuthService
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.MediaType

/**
 * Auth resource
 *
 * Purpose: Defines API endpoint.
 *
 * @property log
 * @property service
 * @constructor Create empty Auth resource
 */
@Traced
@ApplicationScoped
class AuthResource(
    private var log: AuditLog,
    private val service: AuthService
) {
    @Route(
        path = "/auth/token",
        methods = [Route.HttpMethod.POST],
        produces = [MediaType.APPLICATION_JSON]
    )
    fun token(
        @Header(value = APIHeader.AUTHORIZATION) token: String,
        @Header(value = APIHeader.GRANT_TYPE) grantType: String,
        rc: RoutingContext
    ): Uni<SuccessResponse> {
        return Uni.createFrom().voidItem().onItem()
            .invoke { _ ->
                this.log.apiRequest(rc = rc)
            }
            .flatMap { _ ->
                this.service.requestToken(token, grantType)
            }
    }

    @Route(
        path = "/auth/introspect",
        methods = [Route.HttpMethod.GET],
        produces = [MediaType.APPLICATION_JSON]
    )
    fun introspect(
        @Header(value = APIHeader.TOKEN) token: String,
        rc: RoutingContext
    ): Uni<SuccessResponse> {
        return Uni.createFrom().voidItem().onItem()
            .invoke { _ ->
                this.log.apiRequest(rc = rc)
            }
            .flatMap { _ ->
                this.service.introspect(token)
            }
    }
}