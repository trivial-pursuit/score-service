package info.ognibeni.club.score.it

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ScoreServiceApplicationStartupIT(@Autowired val restTemplate: TestRestTemplate) : TestContainerConfiguration {

	@Test
	fun `context loads`() { }

	@Test
	fun `application starts up successfully`() {
		val responseEntity = restTemplate.getForEntity<String>("/actuator/health")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.body).isNotEmpty

		val health = responseEntity.body ?: throw AssertionError("Response body must not be null")

		assertThat(health).startsWith("""{"status":"UP"""")
	}
}
