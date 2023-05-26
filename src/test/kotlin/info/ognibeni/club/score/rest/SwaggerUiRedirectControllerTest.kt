package info.ognibeni.club.score.rest

import info.ognibeni.club.score.ScoreServiceTestConfiguration
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SwaggerUiRedirectController::class)
@ContextConfiguration(classes = [ScoreServiceTestConfiguration::class])
class SwaggerUiRedirectControllerTest(@Autowired private val mockMvc: MockMvc) {

	@ParameterizedTest
	@ValueSource(strings = ["", "/"])
	fun `redirecting root to swagger ui succeeds`(rootPath: String) {
		mockMvc.perform(get(rootPath))
			.andExpect(status().is3xxRedirection)
			.andExpect(redirectedUrl("/swagger-ui/index.html"))
	}
}
