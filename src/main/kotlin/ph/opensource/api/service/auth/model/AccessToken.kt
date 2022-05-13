package ph.opensource.api.service.auth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Access token
 *
 * Purpose: Actual model representation of the API service.
 *
 * @property accessToken
 * @property scope
 * @property tokenType
 * @property expiresIn
 * @constructor Create empty Access token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AccessToken(
    @field:JsonProperty(value = "accessToken")
    val accessToken: String? = null,
    @field:JsonProperty(value = "scope")
    val scope: String? = null,
    @field:JsonProperty(value = "tokenType")
    val tokenType: String? = null,
    @field:JsonProperty(value = "expiresIn")
    val expiresIn: Long? = null,
)
