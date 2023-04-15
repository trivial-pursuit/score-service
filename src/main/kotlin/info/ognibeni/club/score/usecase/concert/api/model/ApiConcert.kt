package info.ognibeni.club.score.usecase.concert.api.model

import info.ognibeni.club.score.usecase.score.api.model.ApiScore
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Concert", description = "Details about a specific concert")
data class ApiConcert(

	@Schema(required = true, description = "The name of the concert", example = "Christmas Concert")
	val name: String,

	@Schema(required = true, description = "The date when the concert took place (as ISO-8601 date)", example = "2022-12-24")
	val date: LocalDate,

	@Schema(required = true, description = "The location of the concert", example = "Royal Concert Hall")
	val location: String,

	@Schema(required = true, description = "The list of pieces that were played")
	val scores: List<ApiScore>
)
