package info.ognibeni.club.score.it

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.web.servlet.client.RestTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class ScoreServiceApplicationStartupIT(@Autowired val restTestClient: RestTestClient) : TestContainerConfiguration {

	@Test
	fun `context loads`() { }

	@Test
	fun `application starts up successfully`() {
		restTestClient.get().uri("/actuator/health")
			.exchange()
			.expectStatus().isOk
			.expectBody()
			.jsonPath("$.status").isEqualTo("UP")
	}
}
