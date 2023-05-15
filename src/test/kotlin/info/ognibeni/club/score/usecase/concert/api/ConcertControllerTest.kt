package info.ognibeni.club.score.usecase.concert.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.ScoreServiceTestConfiguration
import info.ognibeni.club.score.andExpectAuthenticationError
import info.ognibeni.club.score.andExpectResult
import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcert
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcerts
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.logic.RetrieveConcertUseCase
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

@WebMvcTest(ConcertController::class)
@ContextConfiguration(classes = [ScoreServiceTestConfiguration::class])
@WithMockUser
class ConcertControllerTest(@Autowired private val mockMvc: MockMvc,
                            @Autowired private val objectMapper: ObjectMapper) {

	@MockkBean
	lateinit var retrieveConcertUseCase: RetrieveConcertUseCase

	@Test
	fun `retrieving multiple concerts succeeds`() {
		val exampleConcert = listOf(
				exampleConcert("Example Concert 1"),
				exampleConcert("Example Concert 2")
		)
		val expectedApiConcerts = exampleConcert
			.map { it.toApi() }
			.let { ApiConcerts(it) }

		every { retrieveConcertUseCase.getAllConcerts() } returns exampleConcert

		mockMvc.performGetAllConcerts()
				.andExpectResult(objectMapper, expectedApiConcerts)
	}

	@Test
	fun `retrieving empty concert list succeeds`() {
		val exampleConcerts = emptyList<Concert>()
		val expectedApiConcerts = exampleConcerts
			.map { it.toApi() }
			.let { ApiConcerts(it) }

		every { retrieveConcertUseCase.getAllConcerts() } returns exampleConcerts

		mockMvc.performGetAllConcerts()
				.andExpectResult(objectMapper, expectedApiConcerts)
	}

	@WithAnonymousUser
	@Test
	fun `retrieving concerts without authentication fails`() {

		mockMvc.performGetAllConcerts()
			.andExpectAuthenticationError()

		verify { retrieveConcertUseCase wasNot Called }
	}
}


fun MockMvc.performGetAllConcerts(): ResultActions =
		this.perform(get("/concerts"))
