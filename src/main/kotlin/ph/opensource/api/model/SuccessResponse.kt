package ph.opensource.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Success response
 *
 * Purpose: API-level model for success response.
 *
 * @property payload
 * @property signature
 * @constructor Create empty Success response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SuccessResponse(
    @field:JsonProperty(value = "payload")
    val payload: Any? = null,
    @field:JsonProperty(value = "signature")
    val signature: String? = null,
)





