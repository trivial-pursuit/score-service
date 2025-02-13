package info.ognibeni.club.score.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val corsProperties: CorsProperties,
                     private val objectMapper: ObjectMapper) {

	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		http {
			authorizeRequests {
				// Swagger-UI shall always be available
				authorize("/swagger-ui/**", permitAll)
				authorize("/v3/**", permitAll)
				authorize("/", permitAll)
				// Actuator endpoints shall always be available
				authorize("/actuator/**", permitAll)
				authorize("/error", permitAll)
				// Everything else is authenticated
				authorize(anyRequest, authenticated)
			}
			oauth2ResourceServer {
				jwt { }
				authenticationEntryPoint = ProblemJsonAuthenticationEntryPoint(objectMapper)
			}
			cors { }
		}
		return http.build()
	}

	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration().apply {
			allowedOrigins = corsProperties.allowedOrigins
			applyPermitDefaultValues()
		}

		return UrlBasedCorsConfigurationSource().apply {
			registerCorsConfiguration("/**", configuration)
		}
	}
}
