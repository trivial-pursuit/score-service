package info.ognibeni.club.score.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cors", ignoreUnknownFields = false)
data class CorsProperties(
	val allowedOrigins: List<String>
)
