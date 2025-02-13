package info.ognibeni.club.score.it.configuration

import info.ognibeni.club.score.it.TestContainerConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

/**
 * Checks if the API documentation has been generated and exposed properly.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class OpenApiConfigIT(@Autowired val restTemplate: TestRestTemplate) : TestContainerConfiguration {

	@Test
	fun ui_loads_successfully() {

		val responseEntity = restTemplate.getForEntity<String>("/swagger-ui/index.html")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.TEXT_HTML)
		assertThat(responseEntity.body).contains("Swagger UI")
	}

	@Test
	fun api_docs_load_successfully() {

		var responseEntity = restTemplate.getForEntity<String>("/v3/api-docs")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
		assertThat(responseEntity.body).contains("info")
		assertThat(responseEntity.body).contains("paths")
		assertThat(responseEntity.body).contains("components")
		assertThat(responseEntity.body).contains("security")
		assertThat(responseEntity.body).contains(""""openapi":"3.""")

		responseEntity = restTemplate.getForEntity("/v3/api-docs.yaml")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.headers.contentType).isEqualTo(MediaType("application", "vnd.oai.openapi"))
		assertThat(responseEntity.body).contains("info")
		assertThat(responseEntity.body).contains("paths")
		assertThat(responseEntity.body).contains("components")
		assertThat(responseEntity.body).contains("security")
		assertThat(responseEntity.body).contains("openapi: 3.")
	}

	@Test
	fun resources_for_ui_load_successfully() {

		var responseEntity = restTemplate.getForEntity<String>("/v3/api-docs/swagger-config")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
		assertThat(responseEntity.body).contains(""""url":"/v3/api-docs"""")

		responseEntity = restTemplate.getForEntity("/swagger-ui/swagger-ui-bundle.js")

		assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(responseEntity.headers.contentType).isEqualTo(MediaType("text", "javascript"))
	}
}
