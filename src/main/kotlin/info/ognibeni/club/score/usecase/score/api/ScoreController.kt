package info.ognibeni.club.score.usecase.score.api

import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.api.model.toApi
import info.ognibeni.club.score.usecase.score.logic.RetrieveScoreUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/scores"], produces = [APPLICATION_JSON_VALUE])
class ScoreController(private val retrieveScoreUseCase: RetrieveScoreUseCase) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// TODO: @ResponseBody  // necessary for MockMvc?
	fun getAllScores(): List<ApiScore> =
		retrieveScoreUseCase.getAllScores().map { it.toApi() }
}
