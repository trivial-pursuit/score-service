package info.ognibeni.club.score.usecase.score.api.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Composer", description = "Details about a specific composer")
data class ApiComposer(

	@Schema(required = false, description = "The first name of the composer", example = "Wolfgang Amadeus")
	val firstName: String?,

	@Schema(required = true, description = "The last name of the composer", example = "Mozart")
	val lastName: String,

	@Schema(required = false, description = "The birth date of the composer (as ISO-8601 date)", example = "1756-01-27")
	val dayOfBirth: LocalDate?,

	@Schema(required = false, description = "The date of death of the composer (as ISO-8601 date)", example = "1791-12-05")
	val dayOfDeath: LocalDate?
)
