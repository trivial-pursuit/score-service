package info.ognibeni.club.score.it.usecase.concert.api

import com.tngtech.keycloakmock.api.TokenConfig.aTokenConfig
import info.ognibeni.club.score.it.TestContainerConfiguration
import info.ognibeni.club.score.it.TestContainerConfiguration.Companion.oAuthServerMock
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcerts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConcertControllerIT(@LocalServerPort private val port: Int) : TestContainerConfiguration {

	@Test
	fun `retrieving all concerts succeeds`() {

		val apiResponse: ResponseEntity<ApiConcerts> =
			RestClient
				.create()
				.get()
				.uri("http://localhost:$port/concerts")
				.headers { it.setBearerAuth(newAdminToken()) }
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError) { _, _ -> }
				.toEntity<ApiConcerts>()

		assertThat(apiResponse.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(apiResponse.body).isNotNull
		assertThat(apiResponse.body!!.concerts).hasSize(3)
	}

	@Test
	fun `retrieving all concerts without authentication fails`() {

			RestClient
				.create()
				.get()
				.uri("http://localhost:$port/concerts")
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError) { _, response ->
					assertThat(response.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
					assertThat(response.body).isNotNull
//					assertThat(response.body!!.type).isEqualTo(URI("problem:UNAUTHORIZED"))
				}
	}
}

fun newAdminToken(): String = oAuthServerMock.getAccessToken(
	aTokenConfig()
		.withClaim("roles", listOf("admin"))
		.build())
