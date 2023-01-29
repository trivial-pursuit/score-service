package info.ognibeni.club.score.usecase.score

import info.ognibeni.club.score.usecase.score.domain.Score
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/scores"], produces = [APPLICATION_JSON_VALUE])
class RetrieveScoreController(private val retrieveScoreUseCase: RetrieveScoreUseCase) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// @ResponseBody  // necessary for MockMvc
	fun fetchAll(): List<Score> =
		retrieveScoreUseCase.fetchAll()
}
