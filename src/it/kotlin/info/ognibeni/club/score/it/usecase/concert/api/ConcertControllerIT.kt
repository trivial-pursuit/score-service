package info.ognibeni.club.score.it.usecase.concert.api

import info.ognibeni.club.score.it.TestContainerConfiguration
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcert
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer
import org.springframework.boot.web.codec.CodecCustomizer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON
import org.springframework.http.ProblemDetail
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import org.springframework.util.CollectionUtils
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.util.function.Consumer


//@Import(WebTestClientAutoConfiguration::class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConcertControllerIT(@Autowired private val webClient: WebTestClient,
                          @Autowired private val authorizedClientManager: OAuth2AuthorizedClientManager,
                          @Autowired private val applicationContext: ApplicationContext
) : TestContainerConfiguration {

	@Test
	fun `retrieving all concerts succeeds`() {

		val jwt = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuYlFEc2ZrTEttMF9reXVTOGdIMENvakpsN1hJdGpiQVZRZDlRNXJfMmVRIn0.eyJleHAiOjE2ODQ1ODcwMDIsImlhdCI6MTY4NDUwMDYwMiwianRpIjoiMWViMGQ4ZmYtY2I5NC00ODhiLWEzNzktYzdiNmViZWE1MmQ3IiwiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6ODQ0My9yZWFsbXMvc2NvcmUiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNDYxYjEwMjktMmQ1Ny00YWI5LWE0NzQtNGNkMzkwODExZTdlIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic3dhZ2dlci11aSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLXNjb3JlIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImNsaWVudElkIjoic3dhZ2dlci11aSIsImNsaWVudEhvc3QiOiIxNzIuMTguMC4xIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtc3dhZ2dlci11aSIsImNsaWVudEFkZHJlc3MiOiIxNzIuMTguMC4xIn0.OV34ebIHlIIHgYthEjZ4vtnRoXeARBvGWq1u3wPxc0d7Y1PQ0ILpt5YKQ1T4VGWTMQ449HvxvCidvEwl_XipyhRs0VEi8E0ZJwgAA0_S8u0AzeW0m9bNIcWfLEJn6ZKWQFAKQcz7MklDUoBv5TN4W1TnfnP-LAvHy3THHoqlMK8w9Vs023nLL8Gyqa8IGeuHxD3k7HqOHMPYD5biVYsS7EEewa84P0omPcwezQbbVrso8y22PG_FLZucubXml5jMBCw6xixenmGH2ROkyXcgIq5MJ7C91kkGO1nAfR2emlJtiXGEd1j8BQhNrBK1k95LvFc5uH34jRMRaIWTeUmTtg"

		val oauth = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager).also {
			it.setDefaultClientRegistrationId("scoretest")
		}
		val port = applicationContext.environment.getProperty("local.server.port", "8080")
		val baseUrl = "http://localhost:$port"

		val builder = WebTestClient.bindToServer()
		customizeWebTestClientBuilder(builder, applicationContext)
		customizeWebTestClientCodecs(builder, applicationContext)
//		val builder: WebTestClient.Builder = mockServerSpec.configureClient()
		val webTestClient = builder.baseUrl(baseUrl).filter(oauth).build()


//		val wtc = webTestClientBuilder.filter(oauth).build()

		webTestClient.get().uri("/concerts")
//			.headers { it.setBearerAuth(jwt) }
//			.attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("scoretest"))
			.exchange()
			.expectStatus().isOk
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBodyList<ApiConcert>()
			.consumeWith<ListBodySpec<ApiConcert>> {
				val concertList : List<ApiConcert> = it.responseBody ?: throw AssertionError()
				assertThat(concertList).hasSize(3)
			}
	}

	private fun customizeWebTestClientBuilder(clientBuilder: WebTestClient.Builder, context: ApplicationContext) {
		for (customizer in context
			.getBeansOfType(WebTestClientBuilderCustomizer::class.java)
			.values) {
			customizer.customize(clientBuilder)
		}
	}

	private fun customizeWebTestClientCodecs(clientBuilder: WebTestClient.Builder, context: ApplicationContext) {
		val codecCustomizers: Collection<CodecCustomizer?> = context.getBeansOfType(CodecCustomizer::class.java).values
		if (!CollectionUtils.isEmpty(codecCustomizers)) {
			clientBuilder.exchangeStrategies(ExchangeStrategies.builder()
				.codecs { codecs: ClientCodecConfigurer? ->
					codecCustomizers
						.forEach(Consumer { codecCustomizer: CodecCustomizer? -> codecCustomizer!!.customize(codecs) })
				}
				.build())
		}
	}


	@Test
	fun `retrieving all concerts without authentication fails`() {

		webClient.get().uri("/concerts")
			.exchange()
			.expectUnauthorized()
	}
}

fun ResponseSpec.expectUnauthorized(): BodySpec<*, *> {
	return this.expectStatus().isUnauthorized
		.expectHeader().contentType(APPLICATION_PROBLEM_JSON)
		.expectBody<ProblemDetail>()
		.consumeWith {
			assertThat(it.responseBody?.type).isEqualTo(URI("problem:UNAUTHORIZED"))
			assertThat(it.responseBody?.title).isEqualTo("Unauthorized")
			assertThat(it.responseBody?.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
		}
}

@Configuration
class WebClientConfiguration {

	@Bean
	fun authorizedClientManager(clientRegistrationRepository: ClientRegistrationRepository,
	                            authorizedClientRepository: OAuth2AuthorizedClientRepository): OAuth2AuthorizedClientManager {
		val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
			.clientCredentials()
			.build()

		return DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository).also {
			it.setAuthorizedClientProvider(authorizedClientProvider)
		}
	}

	@Bean
	fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager): WebClient {

		val oauth = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager).also {
			it.setDefaultClientRegistrationId("scoretest")
		}

		return WebClient.builder()
			.filter(oauth)
			.build()
	}
}
