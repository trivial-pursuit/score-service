package info.ognibeni.club.score.usecase.score.api.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Score", description = "Details about a specific score")
data class ApiScore(

	@Schema(required = true, description = "Number of the score", example = "123")
	val number: Int,

	@Schema(required = true, description = "Title of the score", example = "Die Zauberflöte")
	val title: String,

	@Schema(required = false, description = "Description of the score", example = "Opera in two acts")
	val description: String?,

	@Schema(required = false, description = "Composer of the score")
	val composer: ApiComposer?,

	@Schema(required = false, description = "Arranger of the score")
	val arranger: ApiArranger?,

	@Schema(required = false, description = "Publisher of the score", example = "G. Henle Verlag")
	val publisher: String?,

	@Schema(required = true, description = "List of the parts belonging to the score")
	val parts: List<ApiPart>
)
