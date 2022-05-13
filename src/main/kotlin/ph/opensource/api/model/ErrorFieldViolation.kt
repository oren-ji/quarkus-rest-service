package ph.opensource.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Error field violation
 *
 * Purpose: API-level model for error field violation.
 *
 * @property field
 * @property message
 * @constructor Create empty Error field violation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorFieldViolation(
    @field:JsonProperty(value = "field")
    val field: String? = null,
    @field:JsonProperty(value = "message")
    val message: String? = null,
)
