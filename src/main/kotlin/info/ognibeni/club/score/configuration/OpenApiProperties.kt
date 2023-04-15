package info.ognibeni.club.score.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "springdoc.swagger-ui.oauth")
data class OpenApiProperties(
	val tokenUrl: String
)
