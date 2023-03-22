package info.ognibeni.club.score.usecase.score.api.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Part", description = "Details about a specific Score Part")
data class ApiPart(

	@Schema(required = true, description = "Name of the Score Part/Instrument", example = "Drums")
	val name: String,

	@Schema(required = false, description = "Description of the Score Part/Instrument", example = "Big drum set")
	val description: String?,

	@Schema(required = true, description = "Digital Score Part is available")
	val analog: Boolean,

	@Schema(required = true, description = "Analog Score Part is available")
	val digital: Boolean
)
