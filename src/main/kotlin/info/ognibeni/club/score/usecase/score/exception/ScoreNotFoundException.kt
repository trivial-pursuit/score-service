package info.ognibeni.club.score.usecase.score.exception

import info.ognibeni.club.score.usecase.score.domain.ScoreNumber
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponseException
import java.net.URI

class ScoreNotFoundException(scoreNumber: ScoreNumber, cause: Throwable? = null) : ErrorResponseException(
	HttpStatus.NOT_FOUND,
	ProblemDetail.forStatus(HttpStatus.NOT_FOUND).apply {
		type = URI.create("problem:SCORE_NOT_FOUND")
		title = "Score not found"
		detail = "Score with number ${scoreNumber.value} not found"
	},
	cause
)
