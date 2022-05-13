package ph.opensource.api.service.auth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Introspect
 *
 * Purpose: Actual model representation of the API service.
 *
 * @property tokenType
 * @property clientId
 * @property active
 * @constructor Create empty Introspect
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Introspect(
    @field:JsonProperty(value = "tokenType")
    val tokenType: String? = null,
    @field:JsonProperty(value = "clientId")
    val clientId: String? = null,
    @field:JsonProperty(value = "active")
    val active: Boolean? = null,
)
