package info.ognibeni.club.score.it.configuration

import info.ognibeni.club.score.it.TestContainerConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody

/**
 * Checks if the API documentation has been generated and exposed properly.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class OpenApiConfigIT(@Autowired val restTestClient: RestTestClient) : TestContainerConfiguration {

	@Test
	fun ui_loads_successfully() {
		restTestClient.get().uri("/swagger-ui/index.html")
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(MediaType.TEXT_HTML)
			.expectBody(String::class.java).consumeWith { assertThat(it.responseBody).contains("Swagger UI") }
	}

	@Test
	fun api_docs_load_successfully() {
		restTestClient.get().uri("/v3/api-docs")
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.openapi").isEqualTo("3.1.0")
			.jsonPath("$.info").exists()
			.jsonPath("$.paths").exists()
			.jsonPath("$.components").exists()
			.jsonPath("$.security").exists()

		restTestClient.get().uri("/v3/api-docs.yaml")
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(MediaType("application", "vnd.oai.openapi"))
			.expectBody<String>()
			.consumeWith {
				assertThat(it.responseBody).contains("openapi: 3.")
				assertThat(it.responseBody).contains("info:")
				assertThat(it.responseBody).contains("paths:")
				assertThat(it.responseBody).contains("components:")
				assertThat(it.responseBody).contains("security:")
			}
	}

	@Test
	fun resources_for_ui_load_successfully() {
		restTestClient.get().uri("/v3/api-docs/swagger-config")
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody().jsonPath("$.url").isEqualTo("/v3/api-docs")

		restTestClient.get().uri("/swagger-ui/swagger-ui-bundle.js")
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(MediaType("text", "javascript"))
	}
}
