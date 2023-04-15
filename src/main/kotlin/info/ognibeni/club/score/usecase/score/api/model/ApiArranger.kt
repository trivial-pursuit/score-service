package info.ognibeni.club.score.usecase.score.api.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Arranger", description = "Details about a specific arranger")
data class ApiArranger(

	@Schema(required = false, description = "The first name of the arranger", example = "Henry")
	val firstName: String?,

	@Schema(required = true, description = "The last name of the arranger", example = "Mancini")
	val lastName: String,

	@Schema(required = false, description = "The birth date of the arranger (as ISO-8601 date)", example = "1924-04-16")
	val dayOfBirth: LocalDate?,

	@Schema(required = false, description = "The date of death of the arranger (as ISO-8601 date)", example = "1994-06-14")
	val dayOfDeath: LocalDate?
)
