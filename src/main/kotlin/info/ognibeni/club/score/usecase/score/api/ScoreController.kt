package info.ognibeni.club.score.usecase.score.api

import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import info.ognibeni.club.score.usecase.score.api.model.toApi
import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import info.ognibeni.club.score.usecase.score.logic.RetrieveScoreUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.HttpURLConnection

@Tag(name = "score", description = "All operations regarding scores")
@RestController
@RequestMapping(path = ["/scores"], produces = [APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE])
class ScoreController(private val retrieveScoreUseCase: RetrieveScoreUseCase) {

	@Operation(summary = "Retrieve all available scores")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_OK.toString(),
			description = "Scores successfully retrieved",
			content = [Content(schema = Schema(implementation = List::class), mediaType = APPLICATION_JSON_VALUE)])])
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// TODO: @ResponseBody  // necessary for MockMvc?
	fun getAllScores(): List<ApiScore> =
		retrieveScoreUseCase.getAllScores().map { it.toApi() }

	@Operation(summary = "Retrieve a specific score")
	@ApiResponses(value = [
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_OK.toString(),
			description = "Score successfully retrieved",
			content = [Content(schema = Schema(implementation = ApiScore::class), mediaType = APPLICATION_JSON_VALUE)]),
		ApiResponse(
			responseCode = HttpURLConnection.HTTP_NOT_FOUND.toString(),
			description = "Specific score was not found",
			content = [Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE)])])
	@GetMapping("/{scoreNumber}")
	@ResponseStatus(HttpStatus.OK)
	fun getScore(
		@PathVariable
		@Parameter(description = "Number of the score", required = true, example = "123")
		scoreNumber: Int): ApiScore =
		retrieveScoreUseCase.getScore(ScoreNumber(scoreNumber)).toApi()
}
