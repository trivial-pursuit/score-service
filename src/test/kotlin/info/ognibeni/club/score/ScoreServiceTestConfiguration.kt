package info.ognibeni.club.score

import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.configuration.CorsProperties
import info.ognibeni.club.score.configuration.SecurityConfig
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.jwt.JwtDecoder

@TestConfiguration
@Import(SecurityConfig::class)
class ScoreServiceTestConfiguration {

	@Bean
	fun corsProperties(): CorsProperties =
        CorsProperties(emptyList())

	/** Necessary (but not used) for [SecurityConfig] */
	@Suppress("unused")
	@MockkBean
	lateinit var jwtDecoder: JwtDecoder
}
