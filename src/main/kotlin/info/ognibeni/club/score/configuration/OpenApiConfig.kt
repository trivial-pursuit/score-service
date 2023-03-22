package info.ognibeni.club.score.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE

@Configuration
class OpenApiConfig(private val properties: OpenApiProperties) {

	@Bean
	fun openApi(): OpenAPI =
		OpenAPI().apply {
			info = Info().apply {
				title = "Score Service"
				description = "A service to manage musical scores and concerts"
				version = OpenApiConfig::class.java.`package`.implementationVersion ?: "local"
				contact = Contact().apply {
					name = "Score Service"
					url = "https://github.com/trivial-pursuit/score-service"
					email = "score-service@ognibeni.info"
				}}

			addSecurityItem(SecurityRequirement().apply { addList(OAUTH2.toString()) })
			components = Components().apply {
				securitySchemes = mapOf(OAUTH2.toString() to SecurityScheme().apply {
					type = OAUTH2
					flows = OAuthFlows().apply { clientCredentials = OAuthFlow().apply { tokenUrl = properties.tokenUrl } }
				})
			}
		}

	@Bean
	fun operationCustomizer(): OperationCustomizer =
		OperationCustomizer { operation, _ ->
			operation.responses.apply {
				addApiResponse(
					HttpStatus.UNAUTHORIZED.value().toString(),
					ApiResponse().apply {
						description = "Missing authentication"
						content = Content().apply { addMediaType(APPLICATION_PROBLEM_JSON_VALUE, MediaType()) }
					}
				)
				addApiResponse(
					HttpStatus.FORBIDDEN.value().toString(),
					ApiResponse().apply {
						description = "No authorization for resource"
						content = Content().apply { addMediaType(APPLICATION_PROBLEM_JSON_VALUE, MediaType()) }
					}
				)
				addApiResponse(
					HttpStatus.INTERNAL_SERVER_ERROR.value().toString(),
					ApiResponse().apply {
						description = "An internal error occurred"
						content = Content().apply { addMediaType(APPLICATION_PROBLEM_JSON_VALUE, MediaType()) }
					}
				)
			}

			operation
		}
}
