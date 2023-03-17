package info.ognibeni.club.score.usecase.score.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.api.model.toApi
import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.logic.RetrieveScoreUseCase
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ScoreController::class)
class ScoreControllerTest(@Autowired private val mockMvc: MockMvc,
                          @Autowired private val objectMapper: ObjectMapper) {

	@MockkBean
	lateinit var retrieveScoreUseCase: RetrieveScoreUseCase

	@Test
	fun `retrieving multiple scores succeeds`() {
		val exampleScores = listOf(
				exampleScore(1, "Example Score 1"),
				exampleScore(2, "Example Score 2")
		)
		val expectedApiScores = exampleScores.map { it.toApi() }

		every { retrieveScoreUseCase.getAllScores() } returns exampleScores

		mockMvc.performGetAllScores()
				.andExpectResult(objectMapper, expectedApiScores)
	}

	@Test
	fun `retrieving empty score list succeeds`() {
		val exampleScores = emptyList<Score>()
		val expectedApiScores = exampleScores.map { it.toApi() }

		every { retrieveScoreUseCase.getAllScores() } returns exampleScores

		mockMvc.performGetAllScores()
				.andExpectResult(objectMapper, expectedApiScores)
	}
}


fun MockMvc.performGetAllScores(): ResultActions =
		this.perform(get("/scores"))

fun ResultActions.andExpectResult(objectMapper: ObjectMapper, expectedResult: Any): ResultActions {
	this
			.andExpect(status().isOk)
			.andExpect(content().json(objectMapper.writeValueAsString(expectedResult)))

	return this
}
