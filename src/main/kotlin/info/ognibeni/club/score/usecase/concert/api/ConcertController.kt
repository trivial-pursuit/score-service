package info.ognibeni.club.score.usecase.concert.api

import info.ognibeni.club.score.usecase.concert.api.model.ApiConcerts
import info.ognibeni.club.score.usecase.concert.api.model.toApi
import info.ognibeni.club.score.usecase.concert.logic.RetrieveConcertUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection

@Tag(name = "concert", description = "All operations regarding concerts")
@RestController
@RequestMapping(path = ["/concerts"], produces = [APPLICATION_JSON_VALUE])
class ConcertController(private val retrieveConcertUseCase: RetrieveConcertUseCase) {

	@Operation(summary = "Retrieve all available concerts")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_OK.toString(),
			description = "Concerts successfully retrieved",
			content = [Content(schema = Schema(implementation = ApiConcerts::class), mediaType = APPLICATION_JSON_VALUE)])])
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAllConcerts(): ApiConcerts =
		retrieveConcertUseCase.getAllConcerts()
			.map { it.toApi() }
			.let { ApiConcerts(it) }
}
