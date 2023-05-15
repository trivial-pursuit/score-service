package info.ognibeni.club.score.it.usecase.concert.api

import info.ognibeni.club.score.it.TestContainerConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.RequestEntity.get
import java.net.URI

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConcertControllerIT(@Autowired val restTemplate: TestRestTemplate) : TestContainerConfiguration {

	@Test
	fun `retrieving all concerts succeeds`() {
		val request = get("/concerts")
			.headers { it.setBearerAuth("token") }
			.build()

		val responseEntity = restTemplate.exchange(request, String::class.java)

//		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
	}

	@Test
	fun `retrieving all concerts without authentication fails`() {
		val request = get("/concerts").build()

		val responseEntity = restTemplate.exchange(request, ProblemDetail::class.java)

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
		assertThat(responseEntity.body?.type).isEqualTo(URI("problem:UNAUTHORIZED"))
		assertThat(responseEntity.body?.title).isEqualTo("Unauthorized")
		assertThat(responseEntity.body?.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
	}
}
