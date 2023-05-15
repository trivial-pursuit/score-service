package info.ognibeni.club.score.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException

class ProblemJsonAuthenticationEntryPointTest {

	private val objectMapper = ObjectMapper().registerKotlinModule()

	private val requestMock = mockk<HttpServletRequest>()
	private val responseMock = mockk<HttpServletResponse>(relaxed = true)
	private val authExceptionMock = mockk<AuthenticationException>()

	private val sut = ProblemJsonAuthenticationEntryPoint(objectMapper)

	@Test
	fun `commencing response succeeds`() {
		val httpUnauthorized = HttpStatus.UNAUTHORIZED.value()

		val statusSlot = slot<Int>()
		val responseBodySlot = slot<String>()
		val headerNames = mutableListOf<String>()
		val headerValues = mutableListOf<String>()

		every { responseMock.status } returns httpUnauthorized
		every { responseMock.status = capture(statusSlot) } just Runs
		every { responseMock.addHeader(capture(headerNames), capture(headerValues)) } just Runs
		every { responseMock.outputStream.println(capture(responseBodySlot)) } just Runs

		sut.commence(requestMock, responseMock, authExceptionMock)

		verify(exactly = 1) { responseMock.status = any() }
		verify(exactly = 2) { responseMock.addHeader(any(), any()) }

		assertThat(statusSlot.captured).isEqualTo(httpUnauthorized)
		assertThat(headerNames).contains(HttpHeaders.WWW_AUTHENTICATE)
		assertThat(headerNames).contains(HttpHeaders.CONTENT_TYPE)
		assertThat(headerValues).contains("Bearer")
		assertThat(headerValues).contains(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
		assertThat(responseBodySlot.captured).contains(httpUnauthorized.toString())
		assertThat(responseBodySlot.captured).contains("problem:UNAUTHORIZED")
	}
}
