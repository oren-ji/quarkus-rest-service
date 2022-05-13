package ph.opensource.api.service.auth.external.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * External introspect
 *
 * Purpose: Reads/maps response from external API.
 *
 * @property tokenType
 * @property clientId
 * @property active
 * @constructor Create empty External introspect
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalIntrospect(
    @field:JsonProperty(value = "token_type")
    val tokenType: String? = null,
    @field:JsonProperty(value = "client_id")
    val clientId: String? = null,
    @field:JsonProperty(value = "active")
    val active: Boolean? = null,
)
