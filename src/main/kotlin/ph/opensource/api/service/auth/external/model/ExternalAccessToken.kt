package ph.opensource.api.service.auth.external.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * External access token
 *
 * Purpose: Reads/maps response from external API.
 *
 * @property accessToken
 * @property scope
 * @property tokenType
 * @property expiresIn
 * @constructor Create empty External access token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAccessToken(
    @field:JsonProperty(value = "access_token")
    val accessToken: String? = null,
    @field:JsonProperty(value = "scope")
    val scope: String? = null,
    @field:JsonProperty(value = "token_type")
    val tokenType: String? = null,
    @field:JsonProperty(value = "expires_in")
    val expiresIn: Long? = null,
)
