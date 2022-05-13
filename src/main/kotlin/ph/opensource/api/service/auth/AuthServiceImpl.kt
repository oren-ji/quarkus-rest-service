package ph.opensource.api.service.auth

import io.opentracing.Tracer
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient
import ph.opensource.api.constant.APIErrorMessage
import ph.opensource.api.log.AuditLog
import ph.opensource.api.model.SuccessResponse
import ph.opensource.api.service.AuthService
import ph.opensource.api.service.auth.external.ExternalAuthClient
import ph.opensource.api.service.auth.model.AccessToken
import ph.opensource.api.service.auth.model.Introspect
import java.time.Duration
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ServiceUnavailableException

/**
 * Auth service impl
 *
 * Purpose: The actual implementation of the Auth Service.
 *
 * @property log
 * @property tracer
 * @property authClient
 * @constructor Create empty Auth service impl
 */
@ApplicationScoped
class AuthServiceImpl(
    private val log: AuditLog,
    private val tracer: Tracer,
    @RestClient private val authClient: ExternalAuthClient,
): AuthService {
    /**
     * Client id
     *
     * Purpose: Retrieves defined client-id on resources/application.properties
     */
    @ConfigProperty(name = "external-auth-service.client-id")
    lateinit var clientId: String

    /**
     * Client secret
     *
     * Purpose: Retrieves defined client-secret on resources/application.properties
     */
    @ConfigProperty(name = "external-auth-service.client-secret")
    lateinit var clientSecret: String

    override fun requestToken(token: String, grantType: String): Uni<SuccessResponse> {
        val traceId = this.tracer.activeSpan().context().toTraceId()

        return Uni.createFrom().voidItem().onItem()
            .invoke { _ ->
                this.log.info(message = "Parsing client credentials.")
            }
            .flatMap { _ ->
                val basicToken = token.split(' ').last()
                val parsedToken = String(Base64.getDecoder().decode(basicToken))
                val splittedToken = parsedToken.split(':')
                val partnerClientId = splittedToken.first()
                val partnerClientSecret = splittedToken.last()

                Uni.createFrom().item(Pair(partnerClientId, partnerClientSecret))
            }
            .invoke { _ ->
                this.log.info(message = "Authenticating client credentials.")
            }
            .flatMap { credentials ->
                this.authClient.requestToken(
                    traceId = traceId,
                    clientId = credentials.first,
                    clientSecret = credentials.second,
                    grantType = grantType,
                    scope = "read write"
                )
                .onFailure().retry()
                    .withBackOff(Duration.ofSeconds(1))
                    .withJitter(0.2)
                    .atMost(2)
                .onFailure().transform { exception ->
                    this.log.exception(data = mapOf("exceptionMessage" to exception.message))
                    throw ServiceUnavailableException(APIErrorMessage.TEMPORARILY_UNAVAILABLE)
                }
            }
            .invoke { _ ->
                this.log.info(message = "Successfully authenticated client credentials.")
            }
            .map {
                SuccessResponse(
                    payload = AccessToken(
                        accessToken = it.accessToken,
                        scope = it.scope,
                        tokenType = it.tokenType,
                        expiresIn = it.expiresIn,
                    )
                )
            }
    }

    override fun introspect(token: String): Uni<SuccessResponse> {
        val traceId = this.tracer.activeSpan().context().toTraceId()

        return Uni.createFrom().voidItem().onItem()
            .invoke { _ ->
                this.log.info(message = "Introspecting access token.")
            }
            .flatMap { _ ->
                this.authClient.introspect(
                    traceId = traceId,
                    clientId = clientId,
                    clientSecret = clientSecret,
                    token = token.split(' ').last(),
                    hint = "access_token"
                )
                .onFailure().retry()
                    .withBackOff(Duration.ofSeconds(1))
                    .withJitter(0.2)
                    .atMost(2)
                .onFailure().transform { exception ->
                    this.log.exception(data = mapOf("exceptionMessage" to exception.message))
                    throw ServiceUnavailableException(APIErrorMessage.TEMPORARILY_UNAVAILABLE)
                }
            }
            .invoke { _ ->
                this.log.info(message = "Successfully introspected access token.")
            }
            .map {
                SuccessResponse(
                    payload = Introspect(
                        tokenType = it.tokenType,
                        clientId = it.clientId,
                        active = it.active
                    )
                )
            }
    }

}