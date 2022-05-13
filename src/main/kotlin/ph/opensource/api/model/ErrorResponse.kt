package ph.opensource.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Error response
 *
 * Purpose: API-level model for error response.
 *
 * @property details
 * @property violation
 * @constructor Create empty Error response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResponse(
    @field:JsonProperty(value = "details")
    val details: String,
    @field:JsonProperty(value = "violations")
    val violation: ArrayList<ErrorFieldViolation>? = null,
)