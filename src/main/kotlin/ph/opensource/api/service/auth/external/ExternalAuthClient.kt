package ph.opensource.api.service.auth.external

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import ph.opensource.api.constant.APIHeader
import ph.opensource.api.filter.HttpClientFilter
import ph.opensource.api.service.auth.external.model.ExternalAccessToken
import ph.opensource.api.service.auth.external.model.ExternalIntrospect
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * External auth client
 *
 * Purpose: Defines an interface of an external API client.
 *
 * Implementation: This maps configuration from resources/application.properties via configKey.
 *
 * @constructor Create empty External auth client
 */
@RegisterProvider(HttpClientFilter::class)
@RegisterRestClient(configKey = "external-auth-service")
interface ExternalAuthClient {
    @POST
    @Consumes(value = [MediaType.APPLICATION_FORM_URLENCODED])
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Path("/oauth-token")
    fun requestToken(
        @HeaderParam(value = APIHeader.TRACE_ID) traceId: String,
        @FormParam(value = "client_id") clientId: String,
        @FormParam(value = "client_secret") clientSecret: String,
        @FormParam(value = "grant_type") grantType: String,
        @FormParam(value = "scope") scope: String,
    ): Uni<ExternalAccessToken>

    @POST
    @Consumes(value = [MediaType.APPLICATION_FORM_URLENCODED])
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Path("/oauth-introspect")
    fun introspect(
        @HeaderParam(value = APIHeader.TRACE_ID) traceId: String,
        @FormParam(value = "client_id") clientId: String,
        @FormParam(value = "client_secret") clientSecret: String,
        @FormParam(value = "token") token: String,
        @FormParam(value = "token_type_hint") hint: String,
    ): Uni<ExternalIntrospect>
}