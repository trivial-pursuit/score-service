package info.ognibeni.club.score.it

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.resttestclient.getForEntity
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestRestTemplate
class ScoreServiceApplicationStartupIT(@Autowired val restTemplate: TestRestTemplate) : TestContainerConfiguration {

	@Test
	fun `context loads`() { }

	@Test
	fun `application starts up successfully`() {
		val responseEntity = restTemplate.getForEntity<String>("/actuator/health")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.body).isNotEmpty

		val health = responseEntity.body ?: throw AssertionError("Response body must not be null")

		assertThat(health).contains(""""status":"UP"""")
	}
}
