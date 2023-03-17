package info.ognibeni.club.score.usecase.score.api.model

data class ApiScore(

	val number: Int,

	val title: String,

	val description: String?,

	val composer: ApiComposer?,

	val arranger: ApiArranger?,

	val publisher: String?,

	val parts: List<ApiPart>
)
