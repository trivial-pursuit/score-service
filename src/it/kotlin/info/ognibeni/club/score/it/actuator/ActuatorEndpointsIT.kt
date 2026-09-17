package info.ognibeni.club.score.it.actuator

import info.ognibeni.club.score.it.TestContainerConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.web.servlet.client.RestTestClient

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
@AutoConfigureRestTestClient
class ActuatorEndpointsIT(@Autowired val restTestClient: RestTestClient) : TestContainerConfiguration {

	@ParameterizedTest
	@EnumSource
	fun `specific Actuator endpoints are enabled`(endpoint: ActuatorEndpoint.Enabled) {
		restTestClient.get().uri(endpoint.url)
			.exchange()
			.expectStatus().isOk
			.expectBody().consumeWith { assertThat(it.responseBody).isNotEmpty() }
	}

	@ParameterizedTest
	@EnumSource
	fun `specific Actuator endpoints are disabled`(endpoint: ActuatorEndpoint.Disabled) {
		restTestClient.get().uri(endpoint.url)
			.exchange()
			.expectStatus().isNotFound
	}
}
