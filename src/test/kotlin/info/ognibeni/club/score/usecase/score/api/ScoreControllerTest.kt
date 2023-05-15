package info.ognibeni.club.score.usecase.score.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.ScoreServiceTestConfiguration
import info.ognibeni.club.score.andExpectAuthenticationError
import info.ognibeni.club.score.andExpectNotFoundError
import info.ognibeni.club.score.andExpectResult
import info.ognibeni.club.score.usecase.score.Fixtures.exampleScore
import info.ognibeni.club.score.usecase.score.api.model.toApi
import info.ognibeni.club.score.usecase.score.domain.Score
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import info.ognibeni.club.score.usecase.score.exception.ScoreNotFoundException
import info.ognibeni.club.score.usecase.score.logic.RetrieveScoreUseCase
import io.mockk.Called
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(ScoreController::class)
@ContextConfiguration(classes = [ScoreServiceTestConfiguration::class])
@WithMockUser
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

	@Test
	fun `retrieving existing score succeeds`() {
		val exampleScore = exampleScore()
		val expectedApiScores = exampleScore.toApi()

		every { retrieveScoreUseCase.getScore(any()) } returns exampleScore

		mockMvc.performGetScore(exampleScore.scoreNumber)
				.andExpectResult(objectMapper, expectedApiScores)

		verify(exactly = 1) { retrieveScoreUseCase.getScore(ScoreNumber(1)) }
	}

	@Test
	fun `retrieving non-existing score fails`() {
		val scoreNumber = ScoreNumber(1)
		every { retrieveScoreUseCase.getScore(any()) } throws ScoreNotFoundException(scoreNumber)

		mockMvc.performGetScore(scoreNumber)
				.andExpectNotFoundError("SCORE_NOT_FOUND", scoreNumber.value.toString())

		verify(exactly = 1) { retrieveScoreUseCase.getScore(scoreNumber) }
	}

	@WithAnonymousUser
	@Test
	fun `retrieving scores without authentication fails`() {

		mockMvc.performGetAllScores()
			.andExpectAuthenticationError()

		mockMvc.performGetScore(ScoreNumber(1))
			.andExpectAuthenticationError()

		verify { retrieveScoreUseCase wasNot Called }
	}
}


fun MockMvc.performGetAllScores(): ResultActions =
		this.perform(get("/scores"))

fun MockMvc.performGetScore(scoreNumber: ScoreNumber): ResultActions =
	this.perform(get("/scores/${scoreNumber.value}"))
