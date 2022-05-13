package ph.opensource.api.service

import io.smallrye.mutiny.Uni
import ph.opensource.api.model.SuccessResponse

/**
 * Auth service
 *
 * Purpose: Defines Auth Service and its expected functionalities.
 *
 * @constructor Create empty Auth service
 */
interface AuthService {
    fun requestToken(token: String, grantType: String): Uni<SuccessResponse>
    fun introspect(token: String): Uni<SuccessResponse>
}