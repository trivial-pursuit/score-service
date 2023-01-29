package info.ognibeni.club.score.usecase.concert

import info.ognibeni.club.score.usecase.concert.domain.Concert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/concerts"], produces = [APPLICATION_JSON_VALUE])
class RetrieveConcertController(private val retrieveConcertUseCase: RetrieveConcertUseCase) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// @ResponseBody  // necessary for MockMvc
	fun fetchAll(): List<Concert> =
		retrieveConcertUseCase.fetchAll()
}
