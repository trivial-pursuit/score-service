package info.ognibeni.club.score.usecase.concert.api

import info.ognibeni.club.score.usecase.concert.api.model.ApiConcert
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.logic.RetrieveConcertUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/concerts"], produces = [APPLICATION_JSON_VALUE])
class ConcertController(private val retrieveConcertUseCase: RetrieveConcertUseCase) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// TODO: @ResponseBody  // necessary for MockMvc?
	fun getAllScores(): List<ApiConcert> =
		retrieveConcertUseCase.getAllConcerts().map { it.toApi() }
}
