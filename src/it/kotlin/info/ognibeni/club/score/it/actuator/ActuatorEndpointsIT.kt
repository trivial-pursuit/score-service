package info.ognibeni.club.score.it.actuator

import info.ognibeni.club.score.it.TestContainerConfiguration
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

class ActuatorEndpoint {
	enum class Enabled(val url: String) {
		ACTUATOR("/actuator"),
		ACTUATOR_HEALTH("/actuator/health"),
		ACTUATOR_INFO("/actuator/info");
	}

	enum class Disabled(val url: String) {
		ACTUATOR_AUTOCONFIG("/actuator/autoconfig"),
		ACTUATOR_ENV("/actuator/env"),
		ACTUATOR_SHUTDOWN("/actuator/shutdown");
	}
}

/**
 * Checks if some specific Spring Boot Actuator endpoints are enabled or disabled.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ActuatorEndpointsIT(@Autowired val restTemplate: TestRestTemplate) : TestContainerConfiguration {

	@ParameterizedTest
	@EnumSource
	fun `specific Actuator endpoints are enabled`(endpoint: ActuatorEndpoint.Enabled) {
		val responseEntity = restTemplate.getForEntity<String>(endpoint.url)

		val errorMessage = "Endpoint ${endpoint.url} is not accessible!"
		assertThat(errorMessage, responseEntity.statusCode, `is`(HttpStatus.OK))
		assertThat(errorMessage, responseEntity.body, `is`(notNullValue()))
	}

	@ParameterizedTest
	@EnumSource
	fun `specific Actuator endpoints are disabled`(endpoint: ActuatorEndpoint.Disabled) {
		val responseEntity = restTemplate.getForEntity<String>(endpoint.url)

		val errorMessage = "Endpoint ${endpoint.url} is accessible, but should be disabled!"
		assertThat(errorMessage, responseEntity.statusCode, `is`(HttpStatus.NOT_FOUND))
	}
}
