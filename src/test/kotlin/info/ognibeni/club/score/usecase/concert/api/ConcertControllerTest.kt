package info.ognibeni.club.score.usecase.concert.api

import com.ninjasquad.springmockk.MockkBean
import info.ognibeni.club.score.usecase.concert.Fixtures.exampleConcert
import info.ognibeni.club.score.usecase.concert.api.model.ApiConcert
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.domain.Concert
import info.ognibeni.club.score.usecase.concert.logic.RetrieveConcertUseCase
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.json.JsonMapper

@WebMvcTest(ConcertController::class)
class ConcertControllerTest(@Autowired private val mockMvc: MockMvc,
                          @Autowired private val jsonMapper: JsonMapper
) {

	@MockkBean
	lateinit var retrieveConcertUseCase: RetrieveConcertUseCase

	@Test
	fun `retrieving multiple concerts succeeds`() {
		val exampleConcert = listOf(
				exampleConcert("Example Concert 1"),
				exampleConcert("Example Concert 2")
		)
		val expectedApiConcerts = exampleConcert.map { it.toApi() }

		every { retrieveConcertUseCase.getAllConcerts() } returns exampleConcert

		mockMvc.performGetAllConcerts()
				.andExpectConcerts(jsonMapper, expectedApiConcerts)
	}

	@Test
	fun `retrieving empty concert list succeeds`() {
		val exampleConcerts = emptyList<Concert>()
		val expectedApiConcerts = exampleConcerts.map { it.toApi() }

		every { retrieveConcertUseCase.getAllConcerts() } returns exampleConcerts

		mockMvc.performGetAllConcerts()
				.andExpectConcerts(jsonMapper, expectedApiConcerts)
	}
}


fun MockMvc.performGetAllConcerts(): ResultActions =
		this.perform(get("/concerts"))

fun ResultActions.andExpectConcerts(jsonMapper: JsonMapper, expectedApiConcerts: List<ApiConcert>): ResultActions {
	this
			.andExpect(status().isOk)
			.andExpect(content().json(jsonMapper.writeValueAsString(expectedApiConcerts)))

	return this
}
