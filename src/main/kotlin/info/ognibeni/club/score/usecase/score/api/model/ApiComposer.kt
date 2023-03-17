package info.ognibeni.club.score.usecase.score.api.model

import java.time.LocalDate

data class ApiComposer(

	val firstName: String?,

	val lastName: String,

	val dayOfBirth: LocalDate?,

	val dayOfDeath: LocalDate?
)
