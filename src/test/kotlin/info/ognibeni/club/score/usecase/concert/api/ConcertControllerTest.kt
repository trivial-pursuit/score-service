package info.ognibeni.club.score.usecase.concert.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcert
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcert
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcerts
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.logic.RetrieveConcertUseCase
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ConcertController::class)
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
				.andExpectConcerts(objectMapper, expectedApiConcerts)
	}

	@Test
	fun `retrieving empty concert list succeeds`() {
		val exampleConcerts = emptyList<Concert>()
		val expectedApiConcerts = exampleConcerts
			.map { it.toApi() }
			.let { ApiConcerts(it) }

		every { retrieveConcertUseCase.getAllConcerts() } returns exampleConcerts

		mockMvc.performGetAllConcerts()
				.andExpectConcerts(objectMapper, expectedApiConcerts)
	}
}


fun MockMvc.performGetAllConcerts(): ResultActions =
		this.perform(get("/concerts"))

fun ResultActions.andExpectConcerts(objectMapper: ObjectMapper, expectedApiConcerts: List<ApiConcert>): ResultActions {
	this
			.andExpect(status().isOk)
			.andExpect(content().json(objectMapper.writeValueAsString(expectedApiConcerts)))

	return this
}
