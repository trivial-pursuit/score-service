package info.ognibeni.club.score.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.web.AuthenticationEntryPoint
import java.net.URI

/**
 * An [AuthenticationEntryPoint] that delegates the failure response handling to a [BearerTokenAuthenticationEntryPoint]
 * but additionally adds a [ProblemDetail] description to the response body (instead of sending an empty response).
 *
 * @see <a href="https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html">Spring OAuth 2.0 Resource Server documentation</a>
 */
class ProblemJsonAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {

	companion object {
		private val bearerTokenAuthenticationEntryPoint = BearerTokenAuthenticationEntryPoint()
	}

	override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {

		// BearerTokenAuthenticationEntryPoint sets the HTTP status code and the "WWW-Authenticate" header
		bearerTokenAuthenticationEntryPoint.commence(request, response, authException)

		val problemDetail = ProblemDetail.forStatus(response.status).apply {
			type = URI.create("problem:UNAUTHORIZED")
		}

		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
		response.outputStream.println(objectMapper.writeValueAsString(problemDetail))
	}

}
