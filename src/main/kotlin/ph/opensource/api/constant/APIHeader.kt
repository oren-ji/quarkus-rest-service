package ph.opensource.api.constant

/**
 * API header
 *
 * Purpose: Defines API headers being used by the API.
 *
 * @constructor Create empty API header
 */
object APIHeader {
    /**
     * Log Header
     */
    const val TRACE_ID = "X-Trace-Id"
    /**
     * Authentication Headers
     */
    const val GRANT_TYPE = "X-Grant-Type"
    const val AUTHORIZATION = "Authorization"
    const val TOKEN = "X-Token"
}